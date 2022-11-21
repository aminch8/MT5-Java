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
    POSITION_MODIFY("POSITION_MODIFY"),
    ORDER_TYPE_BUY("ORDER_TYPE_BUY"),
    ORDER_TYPE_SELL("ORDER_TYPE_SELL"),
    ORDER_TYPE_BUY_LIMIT("ORDER_TYPE_BUY_LIMIT"),
    ORDER_TYPE_SELL_LIMIT("ORDER_TYPE_SELL_LIMIT"),



    ;

    private String value;

}
