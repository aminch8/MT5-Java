package com.mt5.core.domains;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mt5.core.enums.PositionType;
import com.mt5.core.utils.MapperUtil;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Positions {
    private boolean error;
    private List<Position> positions;
    @JsonProperty("server_time")
    private long serverTime;

    public Date getServerTime() {
        return MapperUtil.convertLongToDate(serverTime);
    }

    private void setServerTime(long serverTime) {
        this.serverTime = serverTime;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    private static class Position {
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
}
