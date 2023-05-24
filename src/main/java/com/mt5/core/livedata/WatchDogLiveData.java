package com.mt5.core.livedata;

import com.mt5.core.interfaces.LiveDataRunnable;
import com.mt5.core.interfaces.WatchDogRunnable;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class WatchDogLiveData implements WatchDogRunnable {

    private final AtomicBoolean isConnected = new AtomicBoolean(true);

    private MT5LiveData mt5LiveData;
    private LiveDataRunnable liveDataRunnable;

    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    public WatchDogLiveData(MT5LiveData mt5LiveData, LiveDataRunnable liveDataRunnable) {
        this.mt5LiveData = mt5LiveData;
        this.liveDataRunnable = liveDataRunnable;
    }

    @Override
    public void run() {
        CheckConnectionTask checkConnectionTask = new CheckConnectionTask(liveDataRunnable);
        while (true) {
            boolean isStillRunning = false;
            ScheduledFuture<Boolean> isStillRunningFuture = executorService.schedule(checkConnectionTask,30,TimeUnit.SECONDS);
            try {
                isStillRunning = isStillRunningFuture.get(40,TimeUnit.SECONDS);
                isConnected.set(isStillRunning);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                log.error("Error:"+e);
            }
            if (!isStillRunning){
                mt5LiveData.restoreConnection();
                Thread.currentThread().stop();
            }
        }
    }

    @Override
    public boolean isConnected() {
        return isConnected.get();
    }

}
