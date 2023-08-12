package com.mt5.core.livedata;

import com.mt5.core.domains.Candle;
import com.mt5.core.domains.Tick;
import com.mt5.core.enums.Mt5TimeFrame;
import com.mt5.core.interfaces.LiveDataRunnable;
import com.mt5.core.interfaces.OnCandleUpdate;
import com.mt5.core.interfaces.OnTickUpdate;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Slf4j
class LiveDataRunnableImpl implements LiveDataRunnable {

    private OnCandleUpdate onCandleUpdate;
    private OnTickUpdate onTickUpdate;
    private MT5LiveData mt5LiveData;
    public boolean hasDisconnected=false;

    public LiveDataRunnableImpl(OnCandleUpdate onCandleUpdate, MT5LiveData mt5LiveData) {
       this(onCandleUpdate,null,mt5LiveData);
    }
    public LiveDataRunnableImpl(OnTickUpdate onTickUpdate, MT5LiveData mt5LiveData){
        this(null,onTickUpdate,mt5LiveData);
    }

    public LiveDataRunnableImpl(OnCandleUpdate onCandleUpdate, OnTickUpdate onTickUpdate, MT5LiveData mt5LiveData) {
        this.onCandleUpdate = onCandleUpdate;
        this.onTickUpdate = onTickUpdate;
        this.mt5LiveData=mt5LiveData;
    }

    private boolean checkStatus(String status){
        if (status!=null && status.equals("CONNECTED")){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void run() {
        hasDisconnected=false;
        Thread.currentThread().setName("Live Data Thread");
        while (true){
           try {
               String response = mt5LiveData.getLiveData();
               log.info("response gotten is : "+response);
               JSONObject responseJson = new JSONObject(response);
               String status = responseJson.getString("status");
               if (!checkStatus(status)) {
                   hasDisconnected=true;
                   log.error("TERMINAL IS DISCONNECTED.");
               }else {
                   String symbol  = responseJson.getString("symbol");
                   Mt5TimeFrame timeFrame = Mt5TimeFrame.valueOf(responseJson.getString("timeframe"));
                   if (timeFrame== Mt5TimeFrame.TICK){
                       JSONArray candle = responseJson.getJSONArray("data");
                       ZonedDateTime timeZdt = Instant.ofEpochSecond(candle.getLong(0)).atZone(ZoneOffset.UTC);
                       Number bid = candle.getNumber(1);
                       Number ask = candle.getNumber(2);
                       Tick tick = new Tick(timeZdt,bid,ask,symbol);
                       if (this.onTickUpdate!=null) onTickUpdate.onTickUpdate(tick);
                   }else {
                       JSONArray candle = responseJson.getJSONArray("data");
                       ZonedDateTime openTimeZDT = Instant.ofEpochSecond(candle.getLong(0)).atZone(ZoneOffset.UTC);
                       Number openPrice = candle.getNumber(1);
                       Number highPrice = candle.getNumber(2);
                       Number lowPrice = candle.getNumber(3);
                       Number closePrice = candle.getNumber(4);
                       Number volume = candle.getNumber(5);
                       Candle candleObject = new Candle(openTimeZDT, openPrice,highPrice,lowPrice,closePrice,volume,symbol,timeFrame);
                       if (this.onCandleUpdate!=null) onCandleUpdate.onCandleUpdate(candleObject);
                   }
               }
           }catch (Exception e){
               hasDisconnected=true;
               log.error("Live Data Socket Interrupted");
               e.printStackTrace();
           }
        }

    }

}
