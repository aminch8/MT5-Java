package com.mt5.core.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mt5.core.domains.Positions;
import com.mt5.core.domains.requests.GetHistory;
import com.mt5.core.domains.History;
import com.mt5.core.domains.requests.GetPositions;
import com.mt5.core.enums.TimeFrame;
import com.mt5.core.utils.MapperUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.zeromq.ZMQ;

import java.util.Date;

import static org.zeromq.SocketType.PULL;
import static org.zeromq.SocketType.REQ;

@Slf4j
public class MT5Client {
    private int systemPort = 2201;
    private int dataPort = 2202;
    private String host = "localhost";

    ZMQ.Context context = ZMQ.context(2);
    final ZMQ.Socket pushReq = context.socket(REQ);
    final ZMQ.Socket pullData = context.socket(PULL);

    private MT5Client(MT5ClientFactory mt5ClientFactory){
        this.systemPort = mt5ClientFactory.systemPort;
        this.dataPort = mt5ClientFactory.dataPort;
        this.host = mt5ClientFactory.host;
        pushReq.connect("tcp://"+host+":"+systemPort);
        pullData.connect("tcp://"+host+":"+dataPort);
    }

    public static class MT5ClientFactory {
        private int systemPort;
        private int dataPort;
        private String host;

        public MT5ClientFactory(int systemPort, int dataPort) {
            this.systemPort = systemPort;
            this.dataPort = dataPort;
        }

        public MT5ClientFactory setHost(String host) {
            this.host = host;
            return this;
        }

        public MT5Client build(){
            return new MT5Client(this);
        }
    }

    @SneakyThrows
    public History getHistory(String symbol, Date fromDate, Date toDate, TimeFrame timeFrame){
        GetHistory getHistory = new GetHistory(symbol,fromDate,toDate,timeFrame);

        String requestAsString = getHistory.toRequestString();
        String response = executeRequest(requestAsString);

        return MapperUtil.parseStringHistory(response);
    }

    public String executeRequest(String requestAsString){
        boolean requestSentStatus = pushReq.send(requestAsString.getBytes());
        if (!requestSentStatus){
            throw new RuntimeException("Request was not sent, Socket creation failed.");
        }
        String requestResponse = pushReq.recvStr();
        if (!requestResponse.equalsIgnoreCase("OK")){
            throw new RuntimeException("Response of value OK was not received. Wrong port number.");
        }
        return pullData.recvStr();
    }

    public Positions onGoingPositions(){
        GetPositions getPositions = new GetPositions();
        String requestAsString = getPositions.toRequestString();
        String response = executeRequest(requestAsString);
        Positions positions = null;
        try {
            positions = MapperUtil.getObjectMapper().readValue(response, Positions.class);
        } catch (JsonProcessingException e) {
            log.error("Error parsing positions, Message : " , e);
        }
        return positions;
    }



}
