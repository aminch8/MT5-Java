package com.mt5.core.domains.requests;

import com.mt5.core.enums.Action;

public class GetPositions extends MT5RequestTemplate{

    GetPositions() {
        this.action= Action.POSITIONS;
    }
}
