package com.mt5.core.domains.requests;

import com.mt5.core.enums.Action;
import com.mt5.core.enums.ActionType;

public class ClosePartialPosition extends MT5RequestTemplate{
    public ClosePartialPosition(Long id,String symbolName,Number volume) {
        this.action= Action.TRADE;
        this.actionType= ActionType.POSITION_PARTIAL;
        this.symbol=symbolName;
        this.id=String.valueOf(id);
        this.volume=volume.toString();
    }
}
