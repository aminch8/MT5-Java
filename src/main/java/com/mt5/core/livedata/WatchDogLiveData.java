package com.mt5.core.livedata;

import com.mt5.core.interfaces.LiveDataRunnable;
import com.mt5.core.interfaces.WatchDogRunnable;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class WatchDogLiveData implements WatchDogRunnable {

    private final BlockingQueue<Boolean> watchdogFood = new LinkedBlockingQueue<>();
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
        Thread.currentThread().setName("Connection Watchdog Thread");
        while (true) {
            boolean isStillRunning;
            ScheduledFuture<Boolean> isStillRunningFuture = executorService.schedule(checkConnectionTask,30,TimeUnit.SECONDS);
            try {
                isStillRunning = isStillRunningFuture.get(40,TimeUnit.SECONDS);
                isConnected.set(isStillRunning);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                throw new RuntimeException("UNEXPECTED EXCEPTION IN LIVE DATA WATCHDOG");
            }
            if (!isStillRunning){
                mt5LiveData.restoreConnection();
            }
        }
    }

    @Override
    public boolean isConnected() {
        return isConnected.get();
    }

}
