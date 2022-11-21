package com.mt5.core.utils;

import java.math.BigDecimal;

public class BigDecimalUtil {
    public static BigDecimal getBigDecimal(Number number){
        return new BigDecimal(number.toString());
    }
}
