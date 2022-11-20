package com.mt5.core.domains;

import com.mt5.core.enums.TimeFrame;
import lombok.Data;

import java.util.List;

@Data
public class History {
    private String symbol;
    private TimeFrame timeFrame;
    private List<Candle> candles;

    public History(String symbol, TimeFrame timeFrame) {
        this.symbol = symbol;
        this.timeFrame = timeFrame;
    }
}
