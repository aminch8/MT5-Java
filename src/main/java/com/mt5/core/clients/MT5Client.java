package com.mt5.core.clients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mt5.core.domains.*;
import com.mt5.core.domains.requests.*;
import com.mt5.core.enums.TimeFrame;
import com.mt5.core.exceptions.MT5ResponseErrorException;
import com.mt5.core.exceptions.MT5ResponseParseException;
import com.mt5.core.exceptions.MT5SocketException;
import com.mt5.core.utils.MapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.zeromq.ZMQ;

import java.util.Date;
import java.util.List;

import static org.zeromq.SocketType.PULL;
import static org.zeromq.SocketType.REQ;

@Slf4j
public class MT5Client {
    private int systemPort = 2201;
    private int dataPort = 2202;
    private String host;

    ZMQ.Context context = ZMQ.context(2);
    final ZMQ.Socket pushReq = context.socket(REQ);
    final ZMQ.Socket pullData = context.socket(PULL);

    private MT5Client(MT5ClientFactory mt5ClientFactory) {
        this.systemPort = mt5ClientFactory.systemPort;
        this.dataPort = mt5ClientFactory.dataPort;
        this.host = mt5ClientFactory.host;
        pushReq.connect("tcp://" + host + ":" + systemPort);
        pullData.connect("tcp://" + host + ":" + dataPort);
    }

    public static class MT5ClientFactory {
        private int systemPort;
        private int dataPort;
        private String host="localhost";

        public MT5ClientFactory(int systemPort, int dataPort) {
            this.systemPort = systemPort;
            this.dataPort = dataPort;
        }

        public MT5ClientFactory setHost(String host) {
            this.host = host;
            return this;
        }

        public MT5Client build() {
            return new MT5Client(this);
        }
    }


    public History getHistory(String symbol, Date fromDate, Date toDate, TimeFrame timeFrame) {
        GetHistory getHistory = MT5RequestTemplate.GetHistory(symbol,fromDate,toDate,timeFrame);

        String requestAsString = getHistory.toRequestString();
        String response = executeRequest(requestAsString);

        return MapperUtil.parseStringHistory(response);
    }

    public String executeRequest(String requestAsString) {
        boolean requestSentStatus = pushReq.send(requestAsString.getBytes());
        if (!requestSentStatus) {
            throw new MT5SocketException("Request was not sent, Socket creation failed.");
        }
        String requestResponse = pushReq.recvStr();
        if (!requestResponse.equalsIgnoreCase("OK")) {
            throw new MT5ResponseErrorException("Response of value OK was not received. Wrong port number.");
        }
        return pullData.recvStr();
    }


    public List<Position> getOpenPositions() {
        GetPositions getPositions = MT5RequestTemplate.GetPositions();
        String requestAsString = getPositions.toRequestString();
        String response = executeRequest(requestAsString);
        Positions positions = null;
        try {
            positions = MapperUtil.getObjectMapper().readValue(response, Positions.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new MT5ResponseParseException("Unable to parse response.",e);
        }

        return positions.getPositions();
    }


    public Orders getOpenOrders() {
        GetOpenOrders getOpenOrders = MT5RequestTemplate.GetOpenOrders();
        String requestAsString = getOpenOrders.toRequestString();
        String response = executeRequest(requestAsString);
        Orders orders = new Orders();
        try {
            orders = MapperUtil.getObjectMapper().readValue(response, Orders.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new MT5ResponseParseException("Unable to parse response.",e);
        }
        return orders;
    }


    public ActionTradeResponse closeOrder(long id) {
        CloseOrder closeOrder = MT5RequestTemplate.CloseOrder(id);
        String requestAsString = closeOrder.toRequestString();
        String response = executeRequest(requestAsString);
        ActionTradeResponse actionTradeResponse = new ActionTradeResponse();
        try {
            actionTradeResponse = MapperUtil.getObjectMapper().readValue(response, ActionTradeResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new MT5ResponseParseException("Unable to parse response.",e);
        }

        if (actionTradeResponse.isError())
            throw new MT5ResponseErrorException("Error received in response. " + actionTradeResponse.getDescription());
        return actionTradeResponse;
    }


    public ActionTradeResponse closePartialPosition(long id, Number volume) {
        ClosePartialPosition closePartialPosition = MT5RequestTemplate.ClosePartialPosition(id,volume);
        String requestAsString = closePartialPosition.toRequestString();
        String response = executeRequest(requestAsString);
        ActionTradeResponse actionTradeResponse = new ActionTradeResponse();
        try {
            actionTradeResponse = MapperUtil.getObjectMapper().readValue(response, ActionTradeResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new MT5ResponseParseException("Unable to parse response.",e);
        }
        if (actionTradeResponse.isError())
            throw new MT5ResponseErrorException("Error received in response. " + actionTradeResponse.getDescription());
        return actionTradeResponse;
    }


    public ActionTradeResponse closePosition(long id) {
        ClosePosition closePosition = MT5RequestTemplate.ClosePosition(id);
        String requestAsString = closePosition.toRequestString();
        String response = executeRequest(requestAsString);
        ActionTradeResponse actionTradeResponse = new ActionTradeResponse();
        try {
            actionTradeResponse = MapperUtil.getObjectMapper().readValue(response, ActionTradeResponse.class);
        } catch (JsonProcessingException e) {
            log.error("Exception:",e);
            throw new MT5ResponseParseException("Unable to parse response.",e);
        }
        if (actionTradeResponse.isError())
            throw new MT5ResponseErrorException("Error received in response. " + actionTradeResponse.getDescription());
        return actionTradeResponse;
    }


    public LiveSymbols getLiveSymbols() {
        GetLiveSymbols getLiveSymbols = MT5RequestTemplate.GetLiveSymbols();
        String requestAsString = getLiveSymbols.toRequestString();
        String response = executeRequest(requestAsString);
        LiveSymbols liveSymbols = new LiveSymbols();
        try {
            liveSymbols = MapperUtil.getObjectMapper().readValue(response, LiveSymbols.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new MT5ResponseParseException("Unable to parse response.",e);
        }

        return liveSymbols;
    }


    public ActionTradeResponse modifyPosition(long id, Number stoploss, Number takeprofit) {
        ModifyPosition modifyPosition = MT5RequestTemplate.modifyPosition(id,stoploss,takeprofit);
        String requestAsString = modifyPosition.toRequestString();
        String response = executeRequest(requestAsString);
        ActionTradeResponse actionTradeResponse = new ActionTradeResponse();
        try {
            actionTradeResponse = MapperUtil.getObjectMapper().readValue(response, ActionTradeResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new MT5ResponseParseException("Unable to parse response.",e);
        }
        if (actionTradeResponse.isError())
            log.error("Position modification request returned an error." + actionTradeResponse.getDescription());
        return actionTradeResponse;
    }


    public AccountDetails getAccountDetails() {
        GetAccountDetails getAccountDetails = MT5RequestTemplate.GetAccountDetails();
        String requestAsString = getAccountDetails.toRequestString();
        String response = executeRequest(requestAsString);
        AccountDetails accountDetails = new AccountDetails();
        try {
            accountDetails = MapperUtil.getObjectMapper().readValue(response, AccountDetails.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new MT5ResponseParseException("Unable to parse response.",e);
        }

        return accountDetails;
    }


    public ActionTradeResponse marketBuy(String symbol, Number volume, Number stoploss, Number takeprofit) {
        MarketBuyOrder marketBuyOrder = MT5RequestTemplate.MarketBuyOrder(symbol,volume,stoploss,takeprofit);
        String requestAsString = marketBuyOrder.toRequestString();
        String response = executeRequest(requestAsString);
        ActionTradeResponse actionTradeResponse = new ActionTradeResponse();
        try {
            actionTradeResponse = MapperUtil.getObjectMapper().readValue(response, ActionTradeResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new MT5ResponseParseException("Unable to parse response.",e);
        }
        if (actionTradeResponse.isError())
            throw new MT5ResponseErrorException("Error received in response. " + actionTradeResponse.getDescription());
        return actionTradeResponse;
    }

    public ActionTradeResponse marketBuy(String symbol, Number volume) {
        return marketBuy(symbol, volume, null, null);
    }


    public ActionTradeResponse marketSell(String symbol, Number volume, Number stoploss, Number takeprofit) {
        MarketSellOrder marketSellOrder = MT5RequestTemplate.MarketSellOrder(symbol,volume,stoploss,takeprofit);
        String requestAsString = marketSellOrder.toRequestString();
        String response = executeRequest(requestAsString);
        ActionTradeResponse actionTradeResponse = new ActionTradeResponse();
        try {
            actionTradeResponse = MapperUtil.getObjectMapper().readValue(response, ActionTradeResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new MT5ResponseParseException("Unable to parse response.",e);
        }
        if (actionTradeResponse.isError())
            throw new MT5ResponseErrorException("Error received in response. " + actionTradeResponse.getDescription());
        return actionTradeResponse;
    }


    public ActionTradeResponse limitBuy(String symbol, Number volume, Number price, Number stoploss, Number takeprofit) {
        LimitBuyOrder limitBuyOrder = MT5RequestTemplate.LimitBuyOrder(symbol,volume,price,stoploss,takeprofit);
        String requestAsString = limitBuyOrder.toRequestString();
        String response = executeRequest(requestAsString);
        ActionTradeResponse actionTradeResponse = new ActionTradeResponse();
        try {
            actionTradeResponse = MapperUtil.getObjectMapper().readValue(response, ActionTradeResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new MT5ResponseParseException("Unable to parse response.",e);
        }
        if (actionTradeResponse.isError())
            throw new MT5ResponseErrorException("Error received in response. " + actionTradeResponse.getDescription());
        return actionTradeResponse;
    }

    public ActionTradeResponse limitBuy(String symbol, Number volume, Number price) {
        return limitBuy(symbol, volume, price, null, null);
    }



    public ActionTradeResponse limitSell(String symbol, Number volume, Number price, Number stoploss, Number takeprofit) {
        LimitSellOrder limitSellOrder = MT5RequestTemplate.LimitSellOrder(symbol,volume,price,stoploss,takeprofit);
        String requestAsString = limitSellOrder.toRequestString();
        String response = executeRequest(requestAsString);
        ActionTradeResponse actionTradeResponse = new ActionTradeResponse();
        try {
            actionTradeResponse = MapperUtil.getObjectMapper().readValue(response, ActionTradeResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new MT5ResponseParseException("Unable to parse response.",e);
        }
        if (actionTradeResponse.isError())
            throw new MT5ResponseErrorException("Error received in response. " + actionTradeResponse.getDescription());
        return actionTradeResponse;
    }

    public ActionTradeResponse limitSell(String symbol, Number volume, Number price) {
        return limitSell(symbol, volume, price, null, null);
    }


}
