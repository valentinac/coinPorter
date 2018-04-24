package com.qidiancamp.api.binance.service;

import com.qidiancamp.Exchange;
import com.qidiancamp.api.binance.dto.BinanceException;
import com.qidiancamp.api.binance.dto.trade.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class BinanceTradeServiceRaw extends BinanceBaseService {

  protected BinanceTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public List<BinanceOrder> openOrders(String symbol, Long recvWindow, long timestamp)
      throws BinanceException, IOException {
    return binance.openOrders(symbol, recvWindow, timestamp, super.apiKey, super.signatureCreator);
  }

  public BinanceNewOrder newOrder(
      String symbol,
      OrderSide side,
      OrderType type,
      TimeInForce timeInForce,
      BigDecimal quantity,
      BigDecimal price,
      String newClientOrderId,
      BigDecimal stopPrice,
      BigDecimal icebergQty,
      Long recvWindow,
      long timestamp)
      throws IOException, BinanceException {
    return binance.newOrder(
        symbol,
        side,
        type,
        timeInForce,
        quantity,
        price,
        newClientOrderId,
        stopPrice,
        icebergQty,
        recvWindow,
        timestamp,
        super.apiKey,
        super.signatureCreator);
  }

  public void testNewOrder(
      String symbol,
      OrderSide side,
      OrderType type,
      TimeInForce timeInForce,
      BigDecimal quantity,
      BigDecimal price,
      String newClientOrderId,
      BigDecimal stopPrice,
      BigDecimal icebergQty,
      Long recvWindow,
      long timestamp)
      throws IOException, BinanceException {
    binance.testNewOrder(
        symbol,
        side,
        type,
        timeInForce,
        quantity,
        price,
        newClientOrderId,
        stopPrice,
        icebergQty,
        recvWindow,
        timestamp,
        super.apiKey,
        super.signatureCreator);
  }

  public BinanceOrder orderStatus(
      String symbol, long orderId, String origClientOrderId, Long recvWindow, long timestamp)
      throws IOException, BinanceException {
    return binance.orderStatus(
        symbol,
        orderId,
        origClientOrderId,
        recvWindow,
        timestamp,
        super.apiKey,
        super.signatureCreator);
  }

  public BinanceCancelledOrder cancelOrder(
      String symbol,
      long orderId,
      String origClientOrderId,
      String newClientOrderId,
      Long recvWindow,
      long timestamp)
      throws IOException, BinanceException {
    return binance.cancelOrder(
        symbol,
        orderId,
        origClientOrderId,
        newClientOrderId,
        recvWindow,
        timestamp,
        super.apiKey,
        super.signatureCreator);
  }

  public List<BinanceOrder> allOrders(
      String symbol, Long orderId, Integer limit, Long recvWindow, long timestamp)
      throws BinanceException, IOException {
    return binance.allOrders(
        symbol, orderId, limit, recvWindow, timestamp, super.apiKey, super.signatureCreator);
  }

  public List<BinanceTrade> myTrades(
      String symbol, Integer limit, Long fromId, Long recvWindow, long timestamp)
      throws BinanceException, IOException {
    return binance.myTrades(
        symbol, limit, fromId, recvWindow, timestamp, super.apiKey, super.signatureCreator);
  }

  public BinanceListenKey startUserDataStream() throws IOException {
    return binance.startUserDataStream(apiKey);
  }

  public Map keepAliveDataStream(String listenKey) throws IOException {
    return binance.keepAliveUserDataStream(apiKey, listenKey);
  }

  public Map closeDataStream(String listenKey) throws IOException {
    return binance.closeUserDataStream(apiKey, listenKey);
  }
}
