package com.mt5.core.domains.requests;


import com.mt5.core.enums.ActionType;
import com.mt5.core.enums.TimeFrame;
import com.mt5.core.utils.MapperUtil;
import org.apache.logging.log4j.core.appender.rolling.action.Action;

import java.util.Date;


class GetHistory extends MT5RequestTemplate{

    GetHistory(String symbol, Date fromDate, Date toDate, TimeFrame chartTF) {
        this.action= Action.HISTORY;
        this.actionType= ActionType.DATA;
        this.symbol=symbol;
        this.fromDate = fromDate;
        this.toDate=toDate;
        this.chartTF=chartTF;
    }

}
