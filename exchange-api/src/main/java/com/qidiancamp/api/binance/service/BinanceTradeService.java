package com.qidiancamp.api.binance.service;

import com.qidiancamp.Exchange;
import com.qidiancamp.api.binance.BinanceAdapters;
import com.qidiancamp.api.binance.dto.trade.*;
import com.qidiancamp.currency.Currency;
import com.qidiancamp.currency.CurrencyPair;
import com.qidiancamp.dto.Order;
import com.qidiancamp.dto.marketdata.Trades.TradeSortType;
import com.qidiancamp.dto.trade.*;
import com.qidiancamp.exceptions.ExchangeException;
import com.qidiancamp.exceptions.NotAvailableFromExchangeException;
import com.qidiancamp.service.trade.TradeService;
import com.qidiancamp.service.trade.params.*;
import com.qidiancamp.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import com.qidiancamp.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import com.qidiancamp.service.trade.params.orders.OpenOrdersParams;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class BinanceTradeService extends BinanceTradeServiceRaw implements TradeService {

  public BinanceTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() {
    throw new ExchangeException("You need to provide the currency pair to get the list of open orders.");
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    if (!(params instanceof OpenOrdersParamCurrencyPair)) {
      throw new ExchangeException("You need to provide the currency pair to get the list of open orders.");
    }
    OpenOrdersParamCurrencyPair pairParams = (OpenOrdersParamCurrencyPair) params;
    CurrencyPair pair = pairParams.getCurrencyPair();
    Long recvWindow = (Long) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("recvWindow");
    List<BinanceOrder> binanceOpenOrders = super.openOrders(BinanceAdapters.toSymbol(pair), recvWindow, System.currentTimeMillis());
    List<LimitOrder> openOrders = binanceOpenOrders.stream().map(o -> new LimitOrder(BinanceAdapters.convert(o.side), o.origQty
        , o.executedQty, pair, Long.toString(o.orderId), o.getTime(), o.price))
        .collect(Collectors.toList());
    return new OpenOrders(openOrders);
  }

  @Override
  public String placeMarketOrder(MarketOrder mo) throws IOException {
    Long recvWindow = (Long) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("recvWindow");
    BinanceNewOrder newOrder = super.newOrder(BinanceAdapters.toSymbol(mo.getCurrencyPair()), BinanceAdapters.convert(mo.getType())
        , OrderType.MARKET, TimeInForce.GTC, mo.getOriginalAmount(), null, null, null, null, recvWindow, System.currentTimeMillis());
    return Long.toString(newOrder.orderId);
  }

  @Override
  public String placeLimitOrder(LimitOrder lo) throws IOException {
    Long recvWindow = (Long) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("recvWindow");
    BinanceNewOrder newOrder = super.newOrder(BinanceAdapters.toSymbol(lo.getCurrencyPair()), BinanceAdapters.convert(lo.getType())
        , OrderType.LIMIT, TimeInForce.GTC, lo.getOriginalAmount(), lo.getLimitPrice(), null, null, null, recvWindow, System.currentTimeMillis());
    return Long.toString(newOrder.orderId);
  }

  @Override
  public boolean cancelOrder(String orderId) {
    throw new ExchangeException("You need to provide the currency pair to cancel an order.");
  }

  @Override
  public boolean cancelOrder(CancelOrderParams params) throws IOException {
    if (!(params instanceof CancelOrderByCurrencyPair) && !(params instanceof CancelOrderByIdParams)) {
      throw new ExchangeException("You need to provide the currency pair and the order id to cancel an order.");
    }
    CancelOrderByCurrencyPair paramCurrencyPair = (CancelOrderByCurrencyPair) params;
    CancelOrderByIdParams paramId = (CancelOrderByIdParams) params;
    Long recvWindow = (Long) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("recvWindow");
    super.cancelOrder(BinanceAdapters.toSymbol(paramCurrencyPair.getCurrencyPair()), BinanceAdapters.id(paramId.getOrderId()), null, null,
        recvWindow, System.currentTimeMillis());
    return true;
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    if (!(params instanceof TradeHistoryParamCurrencyPair)) {
      throw new ExchangeException("You need to provide the currency pair to get the user trades.");
    }
    TradeHistoryParamCurrencyPair pairParams = (TradeHistoryParamCurrencyPair) params;
    CurrencyPair pair = pairParams.getCurrencyPair();
    if (pair == null) {
      throw new ExchangeException("You need to provide the currency pair to get the user trades.");
    }

    Integer limit = null;
    if (params instanceof TradeHistoryParamLimit) {
      TradeHistoryParamLimit limitParams = (TradeHistoryParamLimit) params;
      limit = limitParams.getLimit();
    }
    Long fromId = null;
    if (params instanceof TradeHistoryParamsIdSpan) {
      TradeHistoryParamsIdSpan idParams = (TradeHistoryParamsIdSpan) params;

      try {
        fromId = BinanceAdapters.id(idParams.getStartId());
      } catch (Throwable ignored) {
      }
    }

    Long recvWindow = (Long) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("recvWindow");
    List<BinanceTrade> binanceTrades = super.myTrades(BinanceAdapters.toSymbol(pair), limit, fromId, recvWindow, System.currentTimeMillis());
    List<UserTrade> trades = binanceTrades.stream()
        .map(t -> new UserTrade(BinanceAdapters.convertType(t.isBuyer), t.qty, pair, t.price, t.getTime()
            , Long.toString(t.id), Long.toString(t.orderId), t.commission, Currency.getInstance(t.commissionAsset)))
        .collect(Collectors.toList());
    return new UserTrades(trades, TradeSortType.SortByTimestamp);
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new BinanceTradeHistoryParams();
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return new DefaultOpenOrdersParamCurrencyPair();
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) {
    throw new NotAvailableFromExchangeException();
  }

}
