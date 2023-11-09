package com.mt5.core.domains;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mt5.core.domains.requests.rest.PositionRest;
import com.mt5.core.enums.PositionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public Position(PositionRest positionRest) {
        this.id = positionRest.getId();
        this.magic = Long.parseLong(positionRest.getMagic().toString());
        this.symbol = positionRest.getSymbol();
        this.type = positionRest.getPositionType();
        this.timeSetup = positionRest.getTimeSetup().getTime()/1000;
        this.openPrice = positionRest.getOpen();
        this.stopLoss = positionRest.getStoploss();
        this.takeProfit = positionRest.getTakeprofit();
        this.volume = positionRest.getVolume();
    }
}