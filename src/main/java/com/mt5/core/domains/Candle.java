package com.mt5.core.domains;

import com.mt5.core.enums.MT5TimeFrame;
import lombok.*;

import java.time.ZonedDateTime;

@AllArgsConstructor
@Data
public class Candle {
    private ZonedDateTime openTime;
    private Number openPrice;
    private Number highPrice;
    private Number lowPrice;
    private Number closePrice;
    private Number volume;
    private String symbol;
    private MT5TimeFrame timeFrame;

    public Candle(ZonedDateTime openTime, Number openPrice, Number highPrice, Number lowPrice, Number closePrice, Number volume) {
        this.openTime = openTime;
        this.openPrice = openPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.closePrice = closePrice;
        this.volume = volume;
    }

    public Candle(ZonedDateTime openTime, Number openPrice, Number highPrice, Number lowPrice, Number closePrice, Number volume, String symbol) {
        this.openTime = openTime;
        this.openPrice = openPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.closePrice = closePrice;
        this.volume = volume;
        this.symbol = symbol;
    }
}
