package com.mt5.core.domains.requests;

import com.mt5.core.enums.Action;

class GetPositions extends MT5RequestTemplate{

    GetPositions() {
        this.action= Action.POSITIONS;
    }
}
