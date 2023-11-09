package com.mt5.core.domains.requests.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mt5.core.enums.PositionType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class PositionRest {

    @JsonProperty("id")
    private long id;

    @JsonProperty("type")
    private PositionType positionType;

    @JsonProperty("magic")
    private BigDecimal magic;

    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("time_setup")
    private Date timeSetup;

    @JsonProperty("open")
    private BigDecimal open;

    @JsonProperty("price_current")
    private BigDecimal currentPrice;

    @JsonProperty("stoploss")
    private BigDecimal stoploss;

    @JsonProperty("takeprofit")
    private BigDecimal takeprofit;

    @JsonProperty("volume")
    private BigDecimal volume;

}
