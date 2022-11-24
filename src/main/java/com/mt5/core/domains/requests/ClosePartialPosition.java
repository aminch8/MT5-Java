package com.mt5.core.domains.requests;

import com.mt5.core.enums.Action;
import com.mt5.core.enums.ActionType;

class ClosePartialPosition extends MT5RequestTemplate{
    ClosePartialPosition(Long id,Number volume) {
        this.action= Action.TRADE;
        this.actionType= ActionType.POSITION_PARTIAL;
        this.id=String.valueOf(id);
        this.volume=volume.toString();
    }
}
