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


}
