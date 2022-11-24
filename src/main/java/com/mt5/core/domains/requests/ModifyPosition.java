package com.mt5.core.domains.requests;

import com.mt5.core.enums.Action;
import com.mt5.core.enums.ActionType;

class ModifyPosition extends MT5RequestTemplate {

    public ModifyPosition(long id,Number stoploss,Number takeprofit) {
        this.action= Action.TRADE;
        this.actionType = ActionType.POSITION_MODIFY;
        this.id=String.valueOf(id);
        this.stoploss=stoploss!=null?stoploss.toString():null;
        this.takeprofit=takeprofit!=null?takeprofit.toString():null;
    }
}
