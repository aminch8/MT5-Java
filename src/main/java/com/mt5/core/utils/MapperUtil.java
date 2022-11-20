package com.mt5.core.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mt5.core.domains.Candle;
import com.mt5.core.domains.History;
import com.mt5.core.enums.TimeFrame;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class MapperUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false)
            .configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES,false)
            .configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS,false)
            .configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, true);

    public static ObjectMapper getObjectMapper(){
        return objectMapper;
    }

    public static History parseStringHistory(String response){
        JSONObject responseJson = new JSONObject(response);
        String symbol = responseJson.getString("symbol");
        String timeframe = responseJson.getString("timeframe");
        JSONArray data = responseJson.getJSONArray("data");
        History history = new History(symbol, TimeFrame.valueOf(timeframe));
        List<Candle> candles = new ArrayList<>();
        for (int i = 0;i<data.length();i++){
            JSONArray candle = data.getJSONArray(i);
            ZonedDateTime openTimeZDT = Instant.ofEpochSecond(candle.getLong(0)).atZone(ZoneOffset.UTC);
            Number openPrice = candle.getNumber(1);
            Number highPrice = candle.getNumber(2);
            Number lowPrice = candle.getNumber(3);
            Number closePrice = candle.getNumber(4);
            Number volume = candle.getNumber(5);
            Candle candleObject = new Candle(openTimeZDT, openPrice,highPrice,lowPrice,closePrice,volume);
            candles.add(candleObject);
        }
        history.setCandles(candles);
        return history;
    }

}
