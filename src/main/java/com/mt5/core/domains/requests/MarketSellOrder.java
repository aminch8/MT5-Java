package com.mt5.core.domains.requests;

import com.mt5.core.enums.Action;
import com.mt5.core.enums.ActionType;

class MarketSellOrder extends MT5RequestTemplate{
    MarketSellOrder(String symbol, Number volume, Number stoploss, Number takeprofit) {
        this(symbol,volume);
        this.stoploss=stoploss!=null?stoploss.toString():null;
        this.takeprofit=takeprofit!=null?takeprofit.toString():null;

    }

    MarketSellOrder(String symbol, Number volume) {
        this.action= Action.TRADE;
        this.actionType= ActionType.ORDER_TYPE_SELL;
        this.symbol = symbol;
        this.volume=volume.toString();
    }

}
