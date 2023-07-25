package com.mt5.core.domains.requests;

import com.mt5.core.enums.Action;
import com.mt5.core.enums.ActionType;

import java.util.Date;

public class LimitSellOrder extends MT5RequestTemplate{
    LimitSellOrder(String symbol, Number volume, Number price,String expiration) {
        this.action= Action.TRADE;
        this.actionType= ActionType.ORDER_TYPE_SELL_LIMIT;
        this.symbol=symbol;
        this.volume=volume.toString();
        this.price=price.toString();
        this.expiration = expiration;

    }
    LimitSellOrder(String symbol, Number volume, Number price, Number stoploss, Number takeprofit) {
       this(symbol,volume,price,null);
        this.stoploss=stoploss!=null?stoploss.toString():null;
        this.takeprofit=takeprofit!=null?takeprofit.toString():null;
    }

    LimitSellOrder(String symbol, Number volume, Number price, Number stoploss, Number takeprofit,Date expiration) {
        this(symbol,volume,price, String.valueOf(expiration.getTime()/1000));
        this.stoploss=stoploss!=null?stoploss.toString():null;
        this.takeprofit=takeprofit!=null?takeprofit.toString():null;
    }
}
