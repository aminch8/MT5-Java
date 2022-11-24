package com.mt5.core.domains.requests;

import com.mt5.core.enums.Action;
import com.mt5.core.enums.TimeFrame;

class UpdateConfig extends MT5RequestTemplate {

    UpdateConfig(String symbol, TimeFrame chartTF) {
        this.action = Action.CONFIG;
        this.symbol = symbol;
        this.chartTF=chartTF;
    }

}
