package com.mt5.core;


import com.mt5.core.domains.Order;
import com.mt5.core.domains.Orders;
import com.mt5.core.domains.Position;
import com.mt5.core.domains.Positions;
import com.mt5.core.livedata.MT5LiveData;
import com.mt5.core.services.MT5Client;
import com.mt5.core.utils.BigDecimalUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class Main {

    public static void main(String[] args) {
	// write your code here
        MT5Client mt5Client = new MT5Client.MT5ClientFactory(2201,2202).setHost("localhost").build();
        MT5LiveData mt5LiveData = new MT5LiveData.MT5LiveDataFactory(2203,mt5Client).build();
        Positions positions = mt5Client.getOpenPositions();
        List<Position> positionsList = positions.getPositions();
        for (Position position : positionsList) {
            BigDecimal volume = BigDecimalUtil.getBigDecimal(position.getVolume());
            mt5Client.closePartialPosition(position.getId(),position.getSymbol(),volume.divide(BigDecimal.TEN, RoundingMode.CEILING));
        }



    }
}
