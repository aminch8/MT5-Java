package com.mt5.core.domains;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mt5.core.enums.PositionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Position {
    private long id;
    private long magic;
    private String symbol;
    private PositionType type;
    @JsonProperty("time_setup")
    private long timeSetup;
    @JsonProperty("open")
    private Number openPrice;
    @JsonProperty("stoploss")
    private Number stopLoss;
    @JsonProperty("takeprofit")
    private Number takeProfit;
    private Number volume;
}
