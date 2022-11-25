package com.mt5.core.interfaces;

public interface OnConnectionFailure {
    void onBeforeConnectionReset();
    void onAfterConnectionReset();
}
