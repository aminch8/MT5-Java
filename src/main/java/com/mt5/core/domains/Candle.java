package com.mt5.core.domains;

import lombok.*;

import java.math.BigDecimal;
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

    public Candle(ZonedDateTime openTime, Number openPrice, Number highPrice, Number lowPrice, Number closePrice, Number volume) {
        this.openTime = openTime;
        this.openPrice = openPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.closePrice = closePrice;
        this.volume = volume;
    }
}
