package com.mt5.core.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public enum ActionType {
    DATA("DATA"),
    ORDER_CANCEL("ORDER_CANCEL"),
    POSITION_PARTIAL("POSITION_PARTIAL"),
    POSITION_CLOSE_ID("POSITION_CLOSE_ID"),


    ;

    private String value;

}
