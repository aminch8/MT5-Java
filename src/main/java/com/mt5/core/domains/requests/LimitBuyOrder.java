package com.mt5.core.domains.requests;

import com.mt5.core.enums.Action;
import com.mt5.core.enums.ActionType;

public class LimitBuyOrder extends MT5RequestTemplate{
    public LimitBuyOrder(String symbol,Number volume,Number price) {
        this.action= Action.TRADE;
        this.actionType= ActionType.ORDER_TYPE_BUY_LIMIT;
        this.symbol=symbol;
        this.volume=volume.toString();
        this.price=price.toString();
    }
    public LimitBuyOrder(String symbol,Number volume,Number price,Number stoploss,Number takeprofit) {
       this(symbol,volume,price);
        this.stoploss=stoploss!=null?stoploss.toString():null;
        this.takeprofit=takeprofit!=null?takeprofit.toString():null;
    }
}
