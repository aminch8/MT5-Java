package com.mt5.core.domains.requests.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mt5.core.enums.OrderType;
import com.mt5.core.enums.PositionType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderRest {

    @JsonProperty("open")
    private long id;

    @JsonProperty("magic")
    private BigDecimal magic;

    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("type")
    private OrderType orderType;

    @JsonProperty("time_setup")
    private Date timeSetup;

    @JsonProperty("open")
    private BigDecimal price;

    @JsonProperty("stoploss")
    private BigDecimal stopLoss;

    @JsonProperty("takeprofit")
    private BigDecimal takeProfit;

    @JsonProperty("volume")
    private BigDecimal volume;

}
