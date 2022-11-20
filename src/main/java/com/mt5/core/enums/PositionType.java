package com.mt5.core.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PositionType {
    POSITION_TYPE_BUY("POSITION_TYPE_BUY"),
    POSITION_TYPE_SELL("POSITION_TYPE_SELL");
    private String value;
}
