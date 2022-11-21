package com.mt5.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderType {
    ORDER_TYPE_BUY_LIMIT("ORDER_TYPE_BUY_LIMIT"),
    ORDER_TYPE_SELL_LIMIT("ORDER_TYPE_SELL_LIMIT"),
    ORDER_TYPE_BUY_STOP("ORDER_TYPE_BUY_STOP"),
    ORDER_TYPE_SELL_STOP("ORDER_TYPE_SELL_STOP");
    private String value;
}
