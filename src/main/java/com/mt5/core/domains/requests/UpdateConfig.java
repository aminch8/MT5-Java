package com.mt5.core.domains.requests;

import com.mt5.core.enums.Action;
import com.mt5.core.enums.TimeFrame;

public class UpdateConfig extends MT5RequestTemplate {

    public UpdateConfig(String symbol, TimeFrame chartTF) {
        this.action = Action.CONFIG.getValue();
        this.symbol = symbol;
        this.chartTF=chartTF.getValue();
    }

}
