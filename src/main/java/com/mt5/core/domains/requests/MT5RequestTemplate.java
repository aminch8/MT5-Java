package com.mt5.core.domains.requests;

import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.lang.reflect.Field;


public class MT5RequestTemplate {
    String action;
    String actionType;
    String symbol;
    String chartTF;
    String fromDate;
    String toDate;
    String id;
    String magic;
    String volume;
    String price;
    String stoploss;
    String takeprofit;
    String expiration;
    String deviation;
    String comment;


    public String toRequestString(){
       if (StringUtils.isEmpty(action)) action="null";
       if (StringUtils.isEmpty(actionType)) actionType="null";
       if (StringUtils.isEmpty(symbol)) symbol="null";
       if (StringUtils.isEmpty(chartTF)) chartTF="null";
       if (StringUtils.isEmpty(fromDate)) fromDate="null";
       if (StringUtils.isEmpty(toDate)) toDate="null";
       if (StringUtils.isEmpty(id)) id="null";
       if (StringUtils.isEmpty(magic)) magic="null";
       if (StringUtils.isEmpty(volume)) volume="null";
       if (StringUtils.isEmpty(price)) price="null";
       if (StringUtils.isEmpty(stoploss)) stoploss="null";
       if (StringUtils.isEmpty(takeprofit)) takeprofit="null";
       if (StringUtils.isEmpty(expiration)) expiration="null";
       if (StringUtils.isEmpty(deviation)) deviation="null";
       if (StringUtils.isEmpty(comment)) comment="null";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action",action);
        jsonObject.put("actionType",actionType);
        jsonObject.put("symbol",symbol);
        jsonObject.put("chartTF",chartTF);
        jsonObject.put("fromDate",fromDate);
        jsonObject.put("toDate",toDate);
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






}
