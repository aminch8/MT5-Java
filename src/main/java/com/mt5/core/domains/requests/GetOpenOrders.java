package com.mt5.core.domains.requests;

import com.mt5.core.enums.Action;

public class GetOpenOrders extends MT5RequestTemplate{

    public GetOpenOrders() {
        this.action= Action.ORDERS;
    }
}
