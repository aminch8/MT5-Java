package com.mt5.core.domains.requests;

import com.mt5.core.enums.Action;
import com.mt5.core.enums.ActionType;
import com.mt5.core.enums.TimeFrame;

import java.util.Date;


public class GetHistory extends MT5RequestTemplate{

    public GetHistory(String symbol, Date fromDate, Date toDate, TimeFrame chartTF) {
        this.action= Action.HISTORY.getValue();
        this.actionType= ActionType.DATA.getValue();
        this.symbol=symbol;
        this.fromDate = String.valueOf(fromDate.getTime()/1000L);
        this.toDate=String.valueOf(toDate.getTime()/1000L);
        this.chartTF=chartTF.getValue();
    }

}
