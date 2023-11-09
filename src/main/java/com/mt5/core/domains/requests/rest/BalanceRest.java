package com.mt5.core.domains.requests.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BalanceRest {
    @JsonProperty("balance")
    private BigDecimal balance;

    @JsonProperty("equity")
    private BigDecimal equity;

    @JsonProperty("margin")
    private BigDecimal margin;

    @JsonProperty("margin_free")
    private BigDecimal marginFree;

    @JsonProperty("positions_total")
    private int positionsTotal;

    @JsonProperty("deal_total")
    private int dealTotal;

    @JsonProperty("orders_total")
    private int ordersTotal;
}
