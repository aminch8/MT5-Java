package com.mt5.core.domains.requests;

import com.mt5.core.enums.Action;
import com.mt5.core.enums.ActionType;

class ClosePosition extends MT5RequestTemplate{
    ClosePosition(long id) {
        this.action= Action.TRADE;
        this.actionType= ActionType.POSITION_PARTIAL;
        this.id=String.valueOf(id);
    }
}
