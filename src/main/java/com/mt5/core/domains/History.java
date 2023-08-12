package com.mt5.core.domains;

import com.mt5.core.enums.MT5TimeFrame;
import lombok.Data;

import java.util.List;

@Data
public class History {
    private String symbol;
    private MT5TimeFrame timeFrame;
    private List<Candle> candles;

    public History(String symbol, MT5TimeFrame timeFrame) {
        this.symbol = symbol;
        this.timeFrame = timeFrame;
    }
}
