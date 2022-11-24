package com.mt5.core.domains.requests;

import com.mt5.core.enums.Action;
import com.mt5.core.enums.ActionType;

class CloseOrder extends MT5RequestTemplate{
    CloseOrder(long id) {
        this.action= Action.TRADE;
        this.actionType= ActionType.ORDER_CANCEL;
        this.id=String.valueOf(id);
    }
}
