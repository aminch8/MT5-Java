package com.mt5.core.domains.requests;

import com.mt5.core.enums.Action;
import com.mt5.core.enums.ActionType;

public class ClosePosition extends MT5RequestTemplate{
    ClosePosition(long id) {
        this.action= Action.TRADE;
        this.actionType= ActionType.POSITION_CLOSE_ID;
        this.id=String.valueOf(id);
    }
}
