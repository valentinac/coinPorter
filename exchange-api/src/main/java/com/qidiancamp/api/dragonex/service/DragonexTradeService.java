package com.qidiancamp.api.dragonex.service;


import com.qidiancamp.Exchange;
import com.qidiancamp.api.dragonex.dto.trade.*;
import com.qidiancamp.currency.CurrencyPair;
import com.qidiancamp.dto.Order;
import com.qidiancamp.dto.Order.OrderType;
import com.qidiancamp.dto.marketdata.Trades;
import com.qidiancamp.dto.trade.LimitOrder;
import com.qidiancamp.dto.trade.OpenOrders;
import com.qidiancamp.dto.trade.UserTrade;
import com.qidiancamp.dto.trade.UserTrades;
import com.qidiancamp.exceptions.ExchangeException;
import com.qidiancamp.service.trade.TradeService;
import com.qidiancamp.service.trade.params.*;
import com.qidiancamp.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import com.qidiancamp.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import com.qidiancamp.service.trade.params.orders.OpenOrdersParams;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class DragonexTradeService extends DragonexTradeServiceRaw implements TradeService {

  public DragonexTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    throw new ExchangeException("You need to provide the currency pair.");
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    if (!(params instanceof OpenOrdersParamCurrencyPair)) {
      throw new ExchangeException("You need to provide the currency pair.");
    }
    OpenOrdersParamCurrencyPair pairParams = (OpenOrdersParamCurrencyPair) params;
    if (pairParams.getCurrencyPair() == null) {
      throw new ExchangeException("You need to provide the currency pair.");
    }
    long symbolId = exchange.symbolId(pairParams.getCurrencyPair());
    OrderHistoryRequest req =
        new OrderHistoryRequest(symbolId, null, null, 1000, 1 /* pending status*/);
    OrderHistory orderHistory = super.orderHistory(exchange.getOrCreateToken().token, req);

    List<LimitOrder> openOrders =
        orderHistory.getList().stream()
            .map(
                o ->
                    new LimitOrder(
                        o.orderType == 1 ? OrderType.BID : OrderType.ASK,
                        o.volume,
                        exchange.pair(o.symbolId),
                        Long.toString(o.orderId),
                        o.getTimestamp(),
                        o.price))
            .collect(Collectors.toList());
    return new OpenOrders(openOrders);
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    OrderPlacement orderPlacement =
        new OrderPlacement(
            exchange.symbolId(limitOrder.getCurrencyPair()),
            limitOrder.getLimitPrice(),
            limitOrder.getOriginalAmount());
    UserOrder newOrder =
        limitOrder.getType() == Order.OrderType.BID
            ? super.orderBuy(exchange.getOrCreateToken().token, orderPlacement)
            : super.orderSell(exchange.getOrCreateToken().token, orderPlacement);
    return Long.toString(newOrder.orderId);
  }

  @Override
  public boolean cancelOrder(CancelOrderParams params) throws IOException {

    if (!(params instanceof CancelOrderByCurrencyPair)) {
      throw new ExchangeException("You need to provide the currency pair.");
    }
    if (!(params instanceof CancelOrderByIdParams)) {
      throw new ExchangeException("You need to provide the order id.");
    }

    CurrencyPair pair = ((CancelOrderByCurrencyPair) params).getCurrencyPair();
    if (pair == null) {
      throw new ExchangeException("You need to provide the currency pair.");
    }
    long orderId;
    try {
      orderId = Long.valueOf(((CancelOrderByIdParams) params).getOrderId());
    } catch (Throwable e) {
      throw new ExchangeException("You need to provide the order id as a number.", e);
    }
    OrderReference ref = new OrderReference(exchange.symbolId(pair), orderId);
    UserOrder order = super.orderCancel(exchange.getOrCreateToken().token, ref);
    return order.status == 3;
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    if (!(params instanceof TradeHistoryParamCurrencyPair)) {
      throw new ExchangeException("You need to provide the currency pair.");
    }
    TradeHistoryParamCurrencyPair pairParams = (TradeHistoryParamCurrencyPair) params;
    CurrencyPair pair = pairParams.getCurrencyPair();
    if (pair == null) {
      throw new ExchangeException("You need to provide the currency pair.");
    }
    long symbolId = exchange.symbolId(pair);
    Integer direction = null;
    if (params instanceof TradeHistoryParamsSorted) {
      TradeHistoryParamsSorted sort = (TradeHistoryParamsSorted) params;
      direction = sort.getOrder() == TradeHistoryParamsSorted.Order.asc ? 1 : 2;
    }
    DealHistoryRequest req = new DealHistoryRequest(symbolId, direction, null, 1000);
    DealHistory dealHistory = super.dealHistory(exchange.getOrCreateToken().token, req);
    List<UserTrade> trades =
        dealHistory.getList().stream()
            .map(
                d -> {
                  CurrencyPair p = exchange.pair(d.symbolId);
                  return new UserTrade(
                      d.orderType == 1 ? OrderType.BID : OrderType.ASK,
                      d.volume,
                      p,
                      d.price,
                      d.getTimestamp(),
                      d.tradeId,
                      d.orderId,
                      d.charge,
                      p.counter);
                })
            .collect(Collectors.toList());
    return new UserTrades(trades, Trades.TradeSortType.SortByTimestamp);
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new DefaultTradeHistoryParamCurrencyPair();
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return new DefaultOpenOrdersParamCurrencyPair();
  }
}
