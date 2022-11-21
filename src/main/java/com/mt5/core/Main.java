package com.mt5.core;


import com.mt5.core.domains.*;
import com.mt5.core.enums.PositionType;
import com.mt5.core.enums.TimeFrame;
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
        List<Position> positions = mt5Client.getOpenPositions();
        for (Position position : positions) {
            BigDecimal lowernumber = BigDecimal.valueOf(14000);
            BigDecimal highernumber = BigDecimal.valueOf(17000);
            if (position.getType()== PositionType.POSITION_TYPE_BUY){
                mt5Client.modifyPosition(position.getId(),lowernumber,highernumber);
            }else {
                mt5Client.modifyPosition(position.getId(),highernumber,lowernumber);
            }
        }


    }
}
