package com.mt5.core.domains.requests.rest.request;

import com.mt5.core.enums.ActionType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TradeRequestRest {

    private long id;
    private String symbol;
    private ActionType actionType;
    private BigDecimal volume;
    private BigDecimal price;
    private BigDecimal stoploss;
    private BigDecimal takeprofit;


    private TradeRequestRest(){}

    public static TradeRequestRest ofMarketBuy(String symbol,BigDecimal volume,BigDecimal stoploss,BigDecimal takeprofit){
        TradeRequestRest tradeRequestRest = new TradeRequestRest();
        tradeRequestRest.setSymbol(symbol);
        tradeRequestRest.setActionType(ActionType.ORDER_TYPE_BUY);
        tradeRequestRest.setVolume(volume);
        tradeRequestRest.setStoploss(stoploss);
        tradeRequestRest.setTakeprofit(takeprofit);
        return tradeRequestRest;
    }

    public static TradeRequestRest ofMarketSell(String symbol,BigDecimal volume,BigDecimal stoploss,BigDecimal takeprofit){
        TradeRequestRest tradeRequestRest = new TradeRequestRest();
        tradeRequestRest.setSymbol(symbol);
        tradeRequestRest.setActionType(ActionType.ORDER_TYPE_SELL);
        tradeRequestRest.setVolume(volume);
        tradeRequestRest.setStoploss(stoploss);
        tradeRequestRest.setTakeprofit(takeprofit);
        return tradeRequestRest;
    }

    public static TradeRequestRest ofBuyLimit(String symbol,BigDecimal volume,BigDecimal price,BigDecimal stoploss,BigDecimal takeprofit){
        TradeRequestRest tradeRequestRest = new TradeRequestRest();
        tradeRequestRest.setSymbol(symbol);
        tradeRequestRest.setActionType(ActionType.ORDER_TYPE_BUY_LIMIT);
        tradeRequestRest.setVolume(volume);
        tradeRequestRest.setStoploss(stoploss);
        tradeRequestRest.setTakeprofit(takeprofit);
        tradeRequestRest.setPrice(price);
        return tradeRequestRest;
    }

    public static TradeRequestRest ofSellLimit(String symbol,BigDecimal volume,BigDecimal price,BigDecimal stoploss,BigDecimal takeprofit){
        TradeRequestRest tradeRequestRest = new TradeRequestRest();
        tradeRequestRest.setSymbol(symbol);
        tradeRequestRest.setActionType(ActionType.ORDER_TYPE_SELL_LIMIT);
        tradeRequestRest.setVolume(volume);
        tradeRequestRest.setStoploss(stoploss);
        tradeRequestRest.setTakeprofit(takeprofit);
        tradeRequestRest.setPrice(price);
        return tradeRequestRest;
    }

    public static TradeRequestRest ofCancelOrder(long orderId){
        TradeRequestRest tradeRequestRest = new TradeRequestRest();
        tradeRequestRest.setId(orderId);
        tradeRequestRest.setActionType(ActionType.ORDER_CANCEL);
        return tradeRequestRest;
    }

    public static TradeRequestRest ofModifyOrder(long orderId,BigDecimal volume,BigDecimal price,BigDecimal stoploss,BigDecimal takeprofit){
        TradeRequestRest tradeRequestRest = new TradeRequestRest();
        tradeRequestRest.setId(orderId);
        tradeRequestRest.setActionType(ActionType.ORDER_MODIFY);
        tradeRequestRest.setVolume(volume);
        tradeRequestRest.setStoploss(stoploss);
        tradeRequestRest.setTakeprofit(takeprofit);
        tradeRequestRest.setPrice(price);
        return tradeRequestRest;
    }

    public static TradeRequestRest ofClosePartial(long positionId,BigDecimal volume){
        TradeRequestRest tradeRequestRest = new TradeRequestRest();
        tradeRequestRest.setId(positionId);
        tradeRequestRest.setVolume(volume);
        tradeRequestRest.setActionType(ActionType.POSITION_PARTIAL);
        return tradeRequestRest;
    }

    public static TradeRequestRest ofClosePosition(long positionId){
        TradeRequestRest tradeRequestRest = new TradeRequestRest();
        tradeRequestRest.setId(positionId);
        tradeRequestRest.setActionType(ActionType.POSITION_CLOSE_ID);
        return tradeRequestRest;
    }


    public static TradeRequestRest ofCloseAllForSymbol(String symbol){
        TradeRequestRest tradeRequestRest = new TradeRequestRest();
        tradeRequestRest.setSymbol(symbol);
        tradeRequestRest.setActionType(ActionType.POSITION_CLOSE_SYMBOL);
        return tradeRequestRest;
    }


    public static TradeRequestRest ofModifyPosition(long id, BigDecimal stoploss, BigDecimal takeprofit) {
        TradeRequestRest tradeRequestRest = new TradeRequestRest();
        tradeRequestRest.setId(id);
        tradeRequestRest.setStoploss(stoploss);
        tradeRequestRest.setTakeprofit(takeprofit);
        tradeRequestRest.setActionType(ActionType.POSITION_MODIFY);
        return tradeRequestRest;
    }
}
