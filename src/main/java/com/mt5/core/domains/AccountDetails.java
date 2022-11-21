package com.mt5.core.domains;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDetails {
    private boolean error;
    private String broker;
    private String currency;
    private String server;
    @JsonProperty("trading_allowed")
    private int tradingAllowed;
    @JsonProperty("bot_trading")
    private int botTrading;
    private Number balance;
    private Number equity;
    private Number margin;
    @JsonProperty("margin_free")
    private Number marginFree;
    @JsonProperty("margin_level")
    private Number marginLevel;

    public boolean getTradingAllowed() {
        return tradingAllowed==1;
    }

    public void setTradingAllowed(int tradingAllowed) {
        this.tradingAllowed = tradingAllowed;
    }

    public boolean getBotTrading() {
        return botTrading==1;
    }

    public void setBotTrading(int botTrading) {
        this.botTrading = botTrading;
    }
}
