package com.mt5.core.domains.requests;

import com.mt5.core.enums.Action;
import com.mt5.core.enums.ActionType;
import com.mt5.core.enums.TimeFrame;
import com.mt5.core.utils.MapperUtil;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.lang.reflect.Field;
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






}
