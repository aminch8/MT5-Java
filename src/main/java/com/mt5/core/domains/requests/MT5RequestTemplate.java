package com.mt5.core.domains.requests;

import com.mt5.core.enums.Action;
import com.mt5.core.enums.ActionType;
import com.mt5.core.enums.TimeFrame;
import com.mt5.core.utils.MapperUtil;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import java.util.Date;


public class MT5RequestTemplate {
    protected Action action;
    protected ActionType actionType;
    protected String symbol;
    protected TimeFrame chartTF;
    protected Date fromDate;
    protected Date toDate;
    protected String id;
    protected String magic;
    protected String volume;
    protected String price;
    protected String stoploss;
    protected String takeprofit;
    protected String expiration;
    protected String deviation;
    protected String comment;


    public String toRequestString(){
       if (StringUtils.isEmpty(symbol)) symbol="null";
       if (StringUtils.isEmpty(id)) id="null";
       if (StringUtils.isEmpty(magic)) magic="null";
       if (StringUtils.isEmpty(volume)) volume="null";
       if (StringUtils.isEmpty(price)) price="null";
       if (StringUtils.isEmpty(stoploss)) stoploss="null";
       if (StringUtils.isEmpty(takeprofit)) takeprofit="null";
       if (StringUtils.isEmpty(expiration)) expiration="null";
       if (StringUtils.isEmpty(deviation)) deviation="null";
       if (StringUtils.isEmpty(comment)) comment="Algotrading";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action",action!=null?action.getValue():"null");
        jsonObject.put("actionType",actionType!=null?actionType.getValue():"null");
        jsonObject.put("symbol",symbol);
        jsonObject.put("chartTF",chartTF!=null?chartTF.getValue():"null");
        jsonObject.put("fromDate",fromDate!=null? MapperUtil.convertDateToEpochSecond(fromDate):"null");
        jsonObject.put("toDate",toDate!=null? MapperUtil.convertDateToEpochSecond(toDate):"null");
        jsonObject.put("id",id);
        jsonObject.put("magic",magic);
        jsonObject.put("volume",volume);
        jsonObject.put("price",price);
        jsonObject.put("stoploss",stoploss);
        jsonObject.put("takeprofit",takeprofit);
        jsonObject.put("expiration",expiration);
        jsonObject.put("deviation",deviation);
        jsonObject.put("comment",comment);
        return jsonObject.toString();
    }

    public static CloseOrder CloseOrder(long id){
        return new CloseOrder(id) ;
    }

    public static ClosePartialPosition ClosePartialPosition (Long id,Number volume){
        return new ClosePartialPosition(id,volume);
    }
    public static ClosePosition ClosePosition(long id){
        return new ClosePosition(id);
    }
    public static GetAccountDetails GetAccountDetails(){
        return new GetAccountDetails();
    }
    public static GetHistory GetHistory(String symbol, Date fromDate, Date toDate, TimeFrame chartTF){
        return new GetHistory(symbol,fromDate,toDate,chartTF);
    }
    public static GetLiveSymbols GetLiveSymbols(){
        return new GetLiveSymbols();
    }
    public static GetOpenOrders GetOpenOrders(){
        return new GetOpenOrders();
    }
    public static GetPositions GetPositions(){
        return new GetPositions();
    }
    public static LimitBuyOrder LimitBuyOrder(String symbol,Number volume,Number price,Number stoploss,Number takeprofit){
        return new LimitBuyOrder(symbol,volume,price,stoploss,takeprofit);
    }
    public static LimitBuyOrder LimitBuyOrder(String symbol,Number volume,Number price){
        return new LimitBuyOrder(symbol,volume,price);
    }
    public static LimitSellOrder LimitSellOrder(String symbol,Number volume,Number price,Number stoploss,Number takeprofit){
        return new LimitSellOrder(symbol,volume,price,stoploss,takeprofit);
    }
    public static LimitSellOrder LimitSellOrder(String symbol, Number volume, Number price){
        return new LimitSellOrder(symbol,volume,price);
    }
    public static MarketBuyOrder MarketBuyOrder(String symbol, Number volume, Number stoploss, Number takeprofit){
        return new MarketBuyOrder(symbol,volume,stoploss,takeprofit);
    }
    public static MarketBuyOrder MarketBuyOrder(String symbol, Number volume){
        return new MarketBuyOrder(symbol,volume);
    }
    public static MarketSellOrder MarketSellOrder(String symbol, Number volume, Number stoploss, Number takeprofit){
        return new MarketSellOrder(symbol,volume,stoploss,takeprofit);
    }
    public static MarketSellOrder MarketSellOrder(String symbol, Number volume){
        return new MarketSellOrder(symbol,volume);
    }
    public static ModifyPosition modifyPosition(long id,Number stoploss,Number takeprofit){
        return new ModifyPosition(id,stoploss,takeprofit);
    }
    public static UpdateConfig UpdateConfig(String symbol, TimeFrame chartTF){
        return new UpdateConfig(symbol,chartTF);
    }






}
