package com.mt5.core.domains.requests;

import com.mt5.core.enums.Action;

class GetAccountDetails extends MT5RequestTemplate{
    GetAccountDetails() {
        this.action= Action.ACCOUNT;
    }
}
