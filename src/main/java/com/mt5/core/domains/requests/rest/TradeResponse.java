package com.mt5.core.domains.requests.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TradeResponse {

    @JsonProperty("error")
    private long error;

    @JsonProperty("description")
    private String description;

   @JsonProperty("order_id")
    private long orderId;

    @JsonProperty("volume")
    private BigDecimal volume;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("bid")
    private BigDecimal bid;

    @JsonProperty("ask")
    private BigDecimal ask;

    @JsonProperty("function")
    private String function;
}
