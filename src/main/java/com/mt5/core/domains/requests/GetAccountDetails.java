package com.mt5.core.domains.requests;

import com.mt5.core.enums.Action;

public class GetAccountDetails extends MT5RequestTemplate{
    GetAccountDetails() {
        this.action= Action.ACCOUNT;
    }
}
