package com.mt5.core.livedata;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mt5.core.domains.ActionConfigResponse;
import com.mt5.core.domains.requests.MT5RequestTemplate;
import com.mt5.core.domains.requests.UpdateConfig;
import com.mt5.core.enums.TimeFrame;
import com.mt5.core.exceptions.MT5ResponseErrorException;
import com.mt5.core.exceptions.MT5ResponseParseException;
import com.mt5.core.interfaces.LiveDataRunnable;
import com.mt5.core.interfaces.OnCandleUpdate;
import com.mt5.core.interfaces.OnConnectionFailure;
import com.mt5.core.interfaces.OnTickUpdate;
import com.mt5.core.clients.MT5Client;
import com.mt5.core.utils.MapperUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.zeromq.ZMQ;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.zeromq.SocketType.PULL;

@Slf4j
public class MT5LiveData {

    private int livePort = 2203;
    private String host = "localhost";
    private MT5Client mt5Client;

    private ZMQ.Context context = ZMQ.context(1);
    private final ZMQ.Socket pullLive = context.socket(PULL);

    private ExecutorService executorService = Executors.newFixedThreadPool(2);

    private LiveDataRunnableImpl connection;
    @Setter
    @Getter
    private OnConnectionFailure onConnectionFailure;
    private OnCandleUpdate onCandleUpdate;
    private OnTickUpdate onTickUpdate;


    private MT5LiveData(MT5LiveData.MT5LiveDataFactory mt5LiveDataFactory){
        this.livePort = mt5LiveDataFactory.livePort;
        this.host = mt5LiveDataFactory.host;
        this.mt5Client = mt5LiveDataFactory.mt5Client;
        this.onConnectionFailure = mt5LiveDataFactory.onConnectionFailure;
        pullLive.connect("tcp://"+host+":"+livePort);
    }

    public static class MT5LiveDataFactory {
        private int livePort;
        private String host="localhost";
        private MT5Client mt5Client;
        private OnConnectionFailure onConnectionFailure;

        public MT5LiveDataFactory(int livePort, MT5Client mt5Client) {
            this.livePort = livePort;
            this.mt5Client = mt5Client;
        }

        public MT5LiveData.MT5LiveDataFactory setHost(String host) {
            this.host = host;
            return this;
        }

        public MT5LiveData.MT5LiveDataFactory setOnConnectionFailure(OnConnectionFailure onConnectionFailure){
            this.onConnectionFailure=onConnectionFailure;
            return this;
        }

        public MT5LiveData build(){
            return new MT5LiveData(this);
        }
    }


    public ActionConfigResponse addToLiveDataConfig(String symbol, TimeFrame timeFrame){
        UpdateConfig updateConfig = MT5RequestTemplate.UpdateConfig(symbol,timeFrame);
        String requestAsString = updateConfig.toRequestString();
        ActionConfigResponse actionConfigResponse = null;
        try {
            actionConfigResponse =
                    MapperUtil.getObjectMapper().readValue(mt5Client.executeRequest(requestAsString), ActionConfigResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new MT5ResponseParseException("Unable to parse response.",e);
        }
        if (actionConfigResponse.isError())  throw new MT5ResponseErrorException("Error received in response. " + actionConfigResponse.getDescription());
        return actionConfigResponse;
    }

    String getLiveData(){
        return pullLive.recvStr();
    }

    public void startStream(OnCandleUpdate onCandleUpdate,OnTickUpdate onTickUpdate){
        this.onCandleUpdate = onCandleUpdate;
        this.onTickUpdate=onTickUpdate;
        connection = new LiveDataRunnableImpl(onCandleUpdate,onTickUpdate,this);
        executorService.execute(connection);
    }

    public void startStream(OnTickUpdate onTickUpdate){
        this.onTickUpdate=onTickUpdate;
        connection = new LiveDataRunnableImpl(onTickUpdate,this);
        executorService.execute(connection);
    }

    public void startStream(OnCandleUpdate onCandleUpdate){
        this.onCandleUpdate = onCandleUpdate;
        connection = new LiveDataRunnableImpl(onCandleUpdate,this);
        executorService.execute(connection);
    }

    public void checkIfDisconnected(){
        if (connection.hasDisconnected){
            if (onConnectionFailure!=null){
                onConnectionFailure.onBeforeConnectionReset();
                onConnectionFailure.onAfterConnectionReset();
            }
            connection.hasDisconnected=false;
        }
    }


}
