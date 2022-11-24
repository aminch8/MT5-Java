package com.mt5.core.domains.requests;

import com.mt5.core.enums.Action;

class GetOpenOrders extends MT5RequestTemplate{

    GetOpenOrders() {
        this.action= Action.ORDERS;
    }
}
