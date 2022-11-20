package com.mt5.core.livedata;

import com.mt5.core.domains.Candle;
import com.mt5.core.domains.Tick;
import com.mt5.core.enums.TimeFrame;
import com.mt5.core.interfaces.OnCandleUpdate;
import com.mt5.core.interfaces.OnTickUpdate;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class LiveDataRunnable implements Runnable{

    private OnCandleUpdate onCandleUpdate;
    private OnTickUpdate onTickUpdate;
    private MT5LiveData mt5LiveData;

    public LiveDataRunnable(OnCandleUpdate onCandleUpdate,MT5LiveData mt5LiveData) {
        this.onCandleUpdate = onCandleUpdate;
        this.mt5LiveData=mt5LiveData;
    }
    public LiveDataRunnable(OnTickUpdate onTickUpdate,MT5LiveData mt5LiveData){
        this.onTickUpdate = onTickUpdate;
        this.mt5LiveData=mt5LiveData;
    }

    public LiveDataRunnable(OnCandleUpdate onCandleUpdate, OnTickUpdate onTickUpdate,MT5LiveData mt5LiveData) {
        this.onCandleUpdate = onCandleUpdate;
        this.onTickUpdate = onTickUpdate;
        this.mt5LiveData=mt5LiveData;
    }

    @Override
    public void run() {
        while (true){
            String response = mt5LiveData.getLiveData();
            JSONObject responseJson = new JSONObject(response);
            String status = responseJson.getString("status");
            String symbol  = responseJson.getString("symbol");
            TimeFrame timeFrame = TimeFrame.valueOf(responseJson.getString("timeframe"));
            if (timeFrame==TimeFrame.TICK){
                JSONArray candle = responseJson.getJSONArray("data");
                ZonedDateTime timeZdt = Instant.ofEpochSecond(candle.getLong(0)).atZone(ZoneOffset.UTC);
                Number bid = candle.getNumber(1);
                Number ask = candle.getNumber(2);
                Tick tick = new Tick(timeZdt,bid,ask);
                if (this.onTickUpdate!=null) onTickUpdate.onTickUpdate(tick);
            }else {
                JSONArray candle = responseJson.getJSONArray("data");
                ZonedDateTime openTimeZDT = Instant.ofEpochSecond(candle.getLong(0)).atZone(ZoneOffset.UTC);
                Number openPrice = candle.getNumber(1);
                Number highPrice = candle.getNumber(2);
                Number lowPrice = candle.getNumber(3);
                Number closePrice = candle.getNumber(4);
                Number volume = candle.getNumber(5);
                Candle candleObject = new Candle(openTimeZDT, openPrice,highPrice,lowPrice,closePrice,volume);
                
                if (this.onCandleUpdate!=null) onCandleUpdate.onCandleUpdate(candleObject);
            }


        }

    }
}
