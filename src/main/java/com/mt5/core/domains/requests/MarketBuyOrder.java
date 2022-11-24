package com.mt5.core.domains.requests;

import com.mt5.core.enums.Action;
import com.mt5.core.enums.ActionType;

public class MarketBuyOrder extends MT5RequestTemplate{
    MarketBuyOrder(String symbol, Number volume, Number stoploss, Number takeprofit) {
        this(symbol,volume);
        this.stoploss=stoploss!=null?stoploss.toString():null;
        this.takeprofit=takeprofit!=null?takeprofit.toString():null;

    }

    MarketBuyOrder(String symbol, Number volume) {
        this.action= Action.TRADE;
        this.actionType= ActionType.ORDER_TYPE_BUY;
        this.symbol = symbol;
        this.volume=volume.toString();
    }

}
