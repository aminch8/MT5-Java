package com.mt5.core.clients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mt5.core.domains.*;
import com.mt5.core.domains.requests.*;
import com.mt5.core.domains.requests.rest.BalanceRest;
import com.mt5.core.domains.requests.rest.OrderRest;
import com.mt5.core.domains.requests.rest.PositionRest;
import com.mt5.core.domains.requests.rest.TradeResponse;
import com.mt5.core.domains.requests.rest.request.TradeRequestRest;
import com.mt5.core.enums.MT5TimeFrame;
import com.mt5.core.exceptions.MT5ResponseErrorException;
import com.mt5.core.exceptions.MT5ResponseParseException;
import com.mt5.core.exceptions.MT5SocketException;
import com.mt5.core.utils.MapperUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.zeromq.ZMQ;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.zeromq.SocketType.PULL;
import static org.zeromq.SocketType.REQ;

@Slf4j
public class MT5Client {
    private String authToken;
    private int restPort=6542;
    private int systemPort = 2201;
    private int dataPort = 2202;
    private String host;

    ZMQ.Context context = ZMQ.context(10);
    ZMQ.Socket pushReq = context.socket(REQ);
    ZMQ.Socket pullData = context.socket(PULL);
    HttpClient httpClient = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1).connectTimeout(Duration.ofSeconds(10)).build();

    private MT5Client(MT5ClientFactory mt5ClientFactory) {
        this.systemPort = mt5ClientFactory.systemPort;
        this.dataPort = mt5ClientFactory.dataPort;
        this.host = mt5ClientFactory.host;
        this.restPort = mt5ClientFactory.restPort;
        this.authToken = mt5ClientFactory.authToken;
        pushReq.connect("tcp://" + host + ":" + systemPort);
        pullData.connect("tcp://" + host + ":" + dataPort);
    }

    public static class MT5ClientFactory {
        public int restPort=6542;
        private int systemPort=2201;
        private int dataPort=2202;
        private String authToken = "{test-token}";
        private String host="localhost";

        public MT5ClientFactory(int systemPort, int dataPort) {
            this.systemPort = systemPort;
            this.dataPort = dataPort;
        }

        public MT5ClientFactory(int systemPort, int dataPort,int restPort) {
            this.systemPort = systemPort;
            this.dataPort = dataPort;
            this.restPort = restPort;
        }

        public MT5ClientFactory setHost(String host) {
            this.host = host;
            return this;
        }

        MT5ClientFactory setAuthToken(String authToken){
            this.authToken = authToken;
            return this;
        }

        public MT5Client build() {
            return new MT5Client(this);
        }
    }


    public History getHistory(String symbol, Date fromDate, Date toDate, MT5TimeFrame timeFrame) {
        GetHistory getHistory = MT5RequestTemplate.GetHistory(symbol,fromDate,toDate,timeFrame);

        String requestAsString = getHistory.toRequestString();
        String response = executeRequest(requestAsString);

        return MapperUtil.parseStringHistory(response);
    }

    public String executeRequest(String requestAsString) {
        log.info("Push: Sending request : " + requestAsString);
        boolean requestSentStatus = pushReq.send(requestAsString.getBytes());
        if (!requestSentStatus) {
            reconnect();
            throw new MT5SocketException("Request was not sent, Socket creation failed.");
        }
        String requestResponse = pushReq.recvStr();
        log.info("Push: Got response : " + requestResponse);
        if (!requestResponse.equalsIgnoreCase("OK")) {
            reconnect();
            throw new MT5ResponseErrorException("Response of value OK was not received. Wrong port number.");
        }
        String reseponse = pullData.recvStr();
        log.info("Pull: Got Response : " + reseponse);
        return reseponse;
    }

    public BalanceRest getBalance(){
        HttpRequest getRequest = HttpRequest.newBuilder()
                .header("Authorization",this.authToken)
                .uri(URI.create("http://localhost"+":"+restPort+"/balance"))
                .build();
        System.out.println(URI.create("http://localhost"+":"+restPort+"/balance").toString());
        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();
        try {
            HttpResponse<String> response = httpClient.send(getRequest,bodyHandler);
            if (response.statusCode()==200) {
                String body = response.body();
                return MapperUtil.getObjectMapper().readValue(body,BalanceRest.class);
            }else {
                throw new RuntimeException("Status code http not 200!, check EA for more info.");
            }
        } catch (Exception e) {
            log.error("Error:"+e);
            return null;
        }
    }

    public PositionRest getPosition(String positionId){
        HttpRequest getRequest = HttpRequest.newBuilder()
                .header("Authorization",this.authToken)
                .uri(URI.create("http://localhost"+":"+restPort+"/positions/"+positionId))
                .build();
        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();
        try {
            HttpResponse<String> response = httpClient.send(getRequest,bodyHandler);
            if (response.statusCode()==200) {
                String body = response.body();
                return MapperUtil.getObjectMapper().readValue(body,PositionRest.class);
            }else {
                throw new RuntimeException("Status code http not 200!, check EA for more info.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error:"+e);
            return null;
        }
    }

    public List<PositionRest> getPositions(){
        HttpRequest getRequest = HttpRequest.newBuilder()
                .header("Authorization",this.authToken)
                .uri(URI.create("http://localhost"+":"+restPort+"/positions"))
                .build();
        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();
        try {
            HttpResponse<String> response = httpClient.send(getRequest,bodyHandler);
            if (response.statusCode()==200) {
                String body = response.body();
                return List.of(MapperUtil.getObjectMapper().readValue(body,PositionRest[].class));
            }else {
                throw new RuntimeException("Status code http not 200!, check EA for more info.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error:"+e);
            return null;
        }
    }


    public List<OrderRest> getOpenOrders() {
        HttpRequest getRequest = HttpRequest.newBuilder()
                .header("Authorization",this.authToken)
                .uri(URI.create("http://localhost"+":"+restPort+"/orders"))
                .build();
        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();
        try {
            HttpResponse<String> response = httpClient.send(getRequest,bodyHandler);
            if (response.statusCode()==200) {
                String body = response.body();
                return List.of(MapperUtil.getObjectMapper().readValue(body,OrderRest[].class));
            }else {
                throw new RuntimeException("Status code http not 200!, check EA for more info.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error:"+e);
            return null;
        }
    }

    public OrderRest getOpenOrder(String orderId) {
        HttpRequest getRequest = HttpRequest.newBuilder()
                .header("Authorization",this.authToken)
                .uri(URI.create("http://localhost"+":"+restPort+"/orders/"+orderId))
                .build();
        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();
        try {
            HttpResponse<String> response = httpClient.send(getRequest,bodyHandler);
            if (response.statusCode()==200) {
                String body = response.body();
                return MapperUtil.getObjectMapper().readValue(body,OrderRest.class);
            }else {
                throw new RuntimeException("Status code http not 200!, check EA for more info.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error:"+e);
            return null;
        }
    }


    @SneakyThrows
    public TradeResponse closeOrder(long id) {
        TradeRequestRest request = TradeRequestRest.ofCancelOrder(id);
        HttpRequest postRequest = HttpRequest.newBuilder()
                .header("Authorization",this.authToken)
                .uri(URI.create("http://localhost"+":"+restPort+"/trade"))
                .POST(HttpRequest.BodyPublishers.ofString(MapperUtil.getObjectMapper().writeValueAsString(request), StandardCharsets.UTF_8))
                .build();
        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();
        try {
            HttpResponse<String> response = httpClient.send(postRequest,bodyHandler);
            if (response.statusCode()==200) {
                String body = response.body();
                return MapperUtil.getObjectMapper().readValue(body,TradeResponse.class);
            }else {
                throw new RuntimeException("Status code http not 200!, check EA for more info.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error:"+e);
            return null;
        }
    }


    @SneakyThrows
    public TradeResponse closePartialPosition(long id, Number volume) {
        TradeRequestRest request = TradeRequestRest.ofClosePartial(id, new BigDecimal(volume.toString()));
        HttpRequest postRequest = HttpRequest.newBuilder()
                .header("Authorization",this.authToken)
                .uri(URI.create("http://localhost"+":"+restPort+"/trade"))
                .POST(HttpRequest.BodyPublishers.ofString(MapperUtil.getObjectMapper().writeValueAsString(request), StandardCharsets.UTF_8))
                .build();
        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();
        try {
            HttpResponse<String> response = httpClient.send(postRequest,bodyHandler);
            if (response.statusCode()==200) {
                String body = response.body();
                return MapperUtil.getObjectMapper().readValue(body,TradeResponse.class);
            }else {
                throw new RuntimeException("Status code http not 200!, check EA for more info.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error:"+e);
            return null;
        }
    }


    @SneakyThrows
    public TradeResponse closePosition(long id) {
        TradeRequestRest request = TradeRequestRest.ofClosePosition(id);
        HttpRequest postRequest = HttpRequest.newBuilder()
                .header("Authorization",this.authToken)
                .uri(URI.create("http://localhost"+":"+restPort+"/trade"))
                .POST(HttpRequest.BodyPublishers.ofString(MapperUtil.getObjectMapper().writeValueAsString(request), StandardCharsets.UTF_8))
                .build();
        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();
        try {
            HttpResponse<String> response = httpClient.send(postRequest,bodyHandler);
            if (response.statusCode()==200) {
                String body = response.body();
                return MapperUtil.getObjectMapper().readValue(body,TradeResponse.class);
            }else {
                throw new RuntimeException("Status code http not 200!, check EA for more info.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error:"+e);
            return null;
        }
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
            reconnect();
            throw new MT5ResponseParseException("Unable to parse response.",e);
        }
        return liveSymbols;
    }


    @SneakyThrows
    public TradeResponse modifyPosition(long id, Number stoploss, Number takeprofit) {
        TradeRequestRest request = TradeRequestRest.ofModifyPosition(id, new BigDecimal(stoploss.toString()),new BigDecimal(takeprofit.toString()));
        HttpRequest postRequest = HttpRequest.newBuilder()
                .header("Authorization",this.authToken)
                .uri(URI.create("http://localhost"+":"+restPort+"/trade"))
                .POST(HttpRequest.BodyPublishers.ofString(MapperUtil.getObjectMapper().writeValueAsString(request), StandardCharsets.UTF_8))
                .build();
        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();
        try {
            HttpResponse<String> response = httpClient.send(postRequest,bodyHandler);
            if (response.statusCode()==200) {
                String body = response.body();
                return MapperUtil.getObjectMapper().readValue(body,TradeResponse.class);
            }else {
                throw new RuntimeException("Status code http not 200!, check EA for more info.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error:"+e);
            return null;
        }
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
            reconnect();
            throw new MT5ResponseParseException("Unable to parse response.",e);
        }

        return accountDetails;
    }


    @SneakyThrows
    public TradeResponse marketBuy(String symbol, Number volume, Number stoploss, Number takeprofit) {
        TradeRequestRest request = TradeRequestRest.ofMarketBuy(symbol,new BigDecimal(volume.toString()),new BigDecimal(stoploss.toString()),new BigDecimal(takeprofit.toString()));
        HttpRequest postRequest = HttpRequest.newBuilder()
                .header("Authorization",this.authToken)
                .uri(URI.create("http://localhost"+":"+restPort+"/trade"))
                .POST(HttpRequest.BodyPublishers.ofString(MapperUtil.getObjectMapper().writeValueAsString(request), StandardCharsets.UTF_8))
                .build();
        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();
        try {
            HttpResponse<String> response = httpClient.send(postRequest,bodyHandler);
            if (response.statusCode()==200) {
                String body = response.body();
                return MapperUtil.getObjectMapper().readValue(body,TradeResponse.class);
            }else {
                throw new RuntimeException("Status code http not 200!, check EA for more info.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error:"+e);
            return null;
        }
    }

    public TradeResponse marketBuy(String symbol, Number volume) {
        return marketBuy(symbol, volume, BigDecimal.ZERO, BigDecimal.ZERO);
    }


    @SneakyThrows
    public TradeResponse marketSell(String symbol, Number volume, Number stoploss, Number takeprofit) {
        TradeRequestRest request = TradeRequestRest.ofMarketSell(symbol,new BigDecimal(volume.toString()),new BigDecimal(stoploss.toString()),new BigDecimal(takeprofit.toString()));
        HttpRequest postRequest = HttpRequest.newBuilder()
                .header("Authorization",this.authToken)
                .uri(URI.create("http://localhost"+":"+restPort+"/trade"))
                .POST(HttpRequest.BodyPublishers.ofString(MapperUtil.getObjectMapper().writeValueAsString(request), StandardCharsets.UTF_8))
                .build();
        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();
        try {
            HttpResponse<String> response = httpClient.send(postRequest,bodyHandler);
            if (response.statusCode()==200) {
                String body = response.body();
                return MapperUtil.getObjectMapper().readValue(body,TradeResponse.class);
            }else {
                throw new RuntimeException("Status code http not 200!, check EA for more info.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error:"+e);
            return null;
        }
    }


    @SneakyThrows
    public TradeResponse limitBuy(String symbol, Number volume, Number price, Number stoploss, Number takeprofit) {
        TradeRequestRest request = TradeRequestRest.ofBuyLimit(symbol,new BigDecimal(volume.toString()),new BigDecimal(price.toString())
                ,new BigDecimal(stoploss.toString()),new BigDecimal(takeprofit.toString()));
        HttpRequest postRequest = HttpRequest.newBuilder()
                .header("Authorization",this.authToken)
                .uri(URI.create("http://localhost"+":"+restPort+"/trade"))
                .POST(HttpRequest.BodyPublishers.ofString(MapperUtil.getObjectMapper().writeValueAsString(request), StandardCharsets.UTF_8))
                .build();
        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();
        try {
            HttpResponse<String> response = httpClient.send(postRequest,bodyHandler);
            if (response.statusCode()==200) {
                String body = response.body();
                return MapperUtil.getObjectMapper().readValue(body,TradeResponse.class);
            }else {
                throw new RuntimeException("Status code http not 200!, check EA for more info.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error:"+e);
            return null;
        }
    }


    public TradeResponse limitBuy(String symbol, Number volume, Number price) {
        return limitBuy(symbol, volume, price, BigDecimal.ZERO, BigDecimal.ZERO);
    }



    @SneakyThrows
    public TradeResponse limitSell(String symbol, Number volume, Number price, Number stoploss, Number takeprofit) {
        TradeRequestRest request = TradeRequestRest.ofBuyLimit(symbol,new BigDecimal(volume.toString()),new BigDecimal(price.toString())
                ,new BigDecimal(stoploss.toString()),new BigDecimal(takeprofit.toString()));
        HttpRequest postRequest = HttpRequest.newBuilder()
                .header("Authorization",this.authToken)
                .uri(URI.create("http://localhost"+":"+restPort+"/trade"))
                .POST(HttpRequest.BodyPublishers.ofString(MapperUtil.getObjectMapper().writeValueAsString(request), StandardCharsets.UTF_8))
                .build();
        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();
        try {
            HttpResponse<String> response = httpClient.send(postRequest,bodyHandler);
            if (response.statusCode()==200) {
                String body = response.body();
                return MapperUtil.getObjectMapper().readValue(body,TradeResponse.class);
            }else {
                throw new RuntimeException("Status code http not 200!, check EA for more info.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error:"+e);
            return null;
        }
    }


    public TradeResponse limitSell(String symbol, Number volume, Number price) {
        return limitSell(symbol, volume, price, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    public void reconnect(){
        pushReq.disconnect("tcp://" + host + ":" + systemPort);
        pullData.disconnect("tcp://" + host + ":" + dataPort);
        pushReq = context.socket(REQ);
        pullData = context.socket(PULL);
        pushReq.connect("tcp://" + host + ":" + systemPort);
        pullData.connect("tcp://" + host + ":" + dataPort);
    }


}
