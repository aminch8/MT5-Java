package com.mt5.core.livedata;

import com.mt5.core.interfaces.LiveDataRunnable;

import java.util.concurrent.Callable;

class CheckConnectionTask implements Callable<Boolean> {


    private LiveDataRunnable liveDataRunnable;

    public CheckConnectionTask(LiveDataRunnable liveDataRunnable) {
        this.liveDataRunnable = liveDataRunnable;
    }

    @Override
    public Boolean call() throws Exception {
        return liveDataRunnable.isRunning();
    }
}
