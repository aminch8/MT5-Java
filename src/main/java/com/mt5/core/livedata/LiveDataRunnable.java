package com.mt5.core.livedata;

import com.mt5.core.interfaces.OnCandleUpdate;
import com.mt5.core.interfaces.OnTickUpdate;

public class LiveDataRunnable implements Runnable{

    private OnCandleUpdate onCandleUpdate;
    private OnTickUpdate onTickUpdate;
    private MT5LiveData mt5LiveData;

    public LiveDataRunnable(OnCandleUpdate onCandleUpdate,MT5LiveData mt5LiveData) {
        this.onCandleUpdate = onCandleUpdate;
        this.mt5LiveData=mt5LiveData;
    }
    public LiveDataRunnable(OnTickUpdate onTickUpdate,MT5LiveData mt5LiveData){
        this.onTickUpdate = onTickUpdate;
        this.mt5LiveData=mt5LiveData;
    }

    public LiveDataRunnable(OnCandleUpdate onCandleUpdate, OnTickUpdate onTickUpdate,MT5LiveData mt5LiveData) {
        this.onCandleUpdate = onCandleUpdate;
        this.onTickUpdate = onTickUpdate;
        this.mt5LiveData=mt5LiveData;
    }

    @Override
    public void run() {
        while (true){
            String response = mt5LiveData.getLiveData();
            System.out.println(response);
            if (this.onTickUpdate!=null){
                onTickUpdate.onTickUpdate(null);
            }
            if (this.onCandleUpdate!=null){
                onCandleUpdate.onCandleUpdate(null);
            }
        }

    }
}
