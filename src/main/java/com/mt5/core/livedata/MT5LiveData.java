package com.mt5.core.livedata;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mt5.core.domains.ActionConfigResponse;
import com.mt5.core.domains.requests.MT5RequestTemplate;
import com.mt5.core.domains.requests.UpdateConfig;
import com.mt5.core.enums.TimeFrame;
import com.mt5.core.interfaces.OnCandleUpdate;
import com.mt5.core.interfaces.OnTickUpdate;
import com.mt5.core.services.MT5Client;
import com.mt5.core.utils.MapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.zeromq.ZMQ;

import static org.zeromq.SocketType.PULL;

@Slf4j
public class MT5LiveData {

    private int livePort = 2203;
    private String host = "localhost";
    private MT5Client mt5Client;

    private ZMQ.Context context = ZMQ.context(1);
    private final ZMQ.Socket pullLive = context.socket(PULL);

    private MT5LiveData(MT5LiveData.MT5LiveDataFactory mt5LiveDataFactory){
        this.livePort = mt5LiveDataFactory.livePort;
        this.host = mt5LiveDataFactory.host;
        this.mt5Client = mt5LiveDataFactory.mt5Client;
        pullLive.connect("tcp://"+host+":"+livePort);
    }

    public static class MT5LiveDataFactory {
        private int livePort;
        private String host="localhost";
        private MT5Client mt5Client;

        public MT5LiveDataFactory(int livePort, MT5Client mt5Client) {
            this.livePort = livePort;
            this.mt5Client = mt5Client;
        }

        public MT5LiveData.MT5LiveDataFactory setHost(String host) {
            this.host = host;
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
           log.error("Response of update could not be processed",e);
        }
        if (actionConfigResponse.isError()) log.error("Update Liva Data Failed, Message : " + actionConfigResponse.getDescription());
        return actionConfigResponse;
    }

    String getLiveData(){
        return pullLive.recvStr();
    }

    public void startStream(OnCandleUpdate onCandleUpdate,OnTickUpdate onTickUpdate){
        new Thread(new LiveDataRunnable(onCandleUpdate,onTickUpdate,this)).start();
    }

    public void startStream(OnTickUpdate onTickUpdate){
        new Thread(new LiveDataRunnable(onTickUpdate,this)).start();
    }

    public void startStream(OnCandleUpdate onCandleUpdate){
        new Thread(new LiveDataRunnable(onCandleUpdate,this)).start();
    }


}
