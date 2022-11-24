package com.mt5.core.exceptions;

public class MT5ResponseErrorException extends RuntimeException{
    public MT5ResponseErrorException(String message) {
        super(message);
    }

    public MT5ResponseErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
