package com.mt5.core;


import com.mt5.core.livedata.MT5LiveData;
import com.mt5.core.clients.MT5Client;


import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {
	// write your code here
        MT5Client mt5Client = new MT5Client.MT5ClientFactory(2201,2202).setHost("localhost").build();
        MT5LiveData mt5LiveData = new MT5LiveData.MT5LiveDataFactory(2203,mt5Client).build();
        mt5Client.limitSell("BTCUSDD",BigDecimal.valueOf(0.25),BigDecimal.valueOf(17000));

    }
}
