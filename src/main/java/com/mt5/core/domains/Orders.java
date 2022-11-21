package com.mt5.core.domains;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mt5.core.enums.OrderType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders {
    private boolean error;
    private List<Order> orders;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    private static class Order {
        private long id;
        private long magic;
        private String symbol;
        private OrderType type;
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
}
