package com.mt5.core.domains.requests;


import com.mt5.core.enums.Action;
import com.mt5.core.enums.ActionType;
import com.mt5.core.enums.MT5TimeFrame;


import java.util.Date;


public class GetHistory extends MT5RequestTemplate{

    GetHistory(String symbol, Date fromDate, Date toDate, MT5TimeFrame chartTF) {
        this.action= Action.HISTORY;
        this.actionType= ActionType.DATA;
        this.symbol=symbol;
        this.fromDate = fromDate;
        this.toDate=toDate;
        this.chartTF=chartTF;
    }

}
