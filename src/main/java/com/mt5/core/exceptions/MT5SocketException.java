package com.mt5.core.exceptions;

public class MT5SocketException extends RuntimeException {
    public MT5SocketException(String message) {
        super(message);
    }

    public MT5SocketException(String message, Throwable cause) {
        super(message, cause);
    }
}
