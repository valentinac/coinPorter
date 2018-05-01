package com.qidiancamp.api.huobi.service;

import com.qidiancamp.Exchange;
import com.qidiancamp.dto.Order;
import com.qidiancamp.dto.trade.*;
import com.qidiancamp.api.huobi.HuobiAdapters;
import com.qidiancamp.api.huobi.dto.trade.HuobiOrder;
import com.qidiancamp.service.trade.TradeService;
import com.qidiancamp.service.trade.params.CancelOrderByIdParams;
import com.qidiancamp.service.trade.params.CancelOrderParams;
import com.qidiancamp.service.trade.params.TradeHistoryParams;
import com.qidiancamp.service.trade.params.orders.OpenOrdersParams;

import java.io.IOException;
import java.util.Collection;

public class HuobiTradeService extends HuobiTradeServiceRaw implements TradeService {

  public HuobiTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams tradeHistoryParams) throws IOException {
    return null;
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) throws IOException {
    return HuobiAdapters.adaptOrders(getHuobiOrder(orderIds));
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return null;
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return null;
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    return cancelHuobiOrder(orderId).length() > 0;
  }

  @Override
  public boolean cancelOrder(CancelOrderParams cancelOrderParams) throws IOException {
    return cancelOrderParams instanceof CancelOrderByIdParams
        && cancelOrder(((CancelOrderByIdParams) cancelOrderParams).getOrderId());
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    return placeHuobiMarketOrder(marketOrder);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams openOrdersParams) throws IOException {
    HuobiOrder[] openOrders = getHuobiOpenOrders();
    return HuobiAdapters.adaptOpenOrders(openOrders);
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    return placeHuobiLimitOrder(limitOrder);
  }

  @Override
  public String placeStopOrder(StopOrder stopOrder) throws IOException {
    return null;
  }
}
