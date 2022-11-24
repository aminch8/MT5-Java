package com.mt5.core.exceptions;

public class MT5ResponseParseException extends RuntimeException {

    public MT5ResponseParseException(String message) {
        super(message);
    }

    public MT5ResponseParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
