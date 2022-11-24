package com.mt5.core.domains.requests;

import com.mt5.core.enums.Action;

class GetLiveSymbols extends MT5RequestTemplate{
    GetLiveSymbols() {
        this.action= Action.LIVESYMBOLS;
    }
}
