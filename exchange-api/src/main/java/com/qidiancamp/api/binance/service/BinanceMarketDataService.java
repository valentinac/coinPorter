package com.qidiancamp.api.binance.service;

import com.qidiancamp.Exchange;
import com.qidiancamp.api.binance.BinanceAdapters;
import com.qidiancamp.api.binance.dto.marketdata.BinanceAggTrades;
import com.qidiancamp.api.binance.dto.marketdata.BinanceOrderbook;
import com.qidiancamp.api.binance.dto.marketdata.BinanceTicker24h;
import com.qidiancamp.currency.CurrencyPair;
import com.qidiancamp.dto.Order.OrderType;
import com.qidiancamp.dto.marketdata.OrderBook;
import com.qidiancamp.dto.marketdata.Ticker;
import com.qidiancamp.dto.marketdata.Trade;
import com.qidiancamp.dto.marketdata.Trades;
import com.qidiancamp.dto.marketdata.Trades.TradeSortType;
import com.qidiancamp.dto.trade.LimitOrder;
import com.qidiancamp.service.marketdata.MarketDataService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class BinanceMarketDataService extends BinanceMarketDataServiceRaw implements MarketDataService {

  public BinanceMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair pair, Object... args) throws IOException {
    BinanceOrderbook ob = getBinanceOrderbook(BinanceAdapters.toSymbol(pair), null);
    List<LimitOrder> bids = ob.bids.entrySet().stream().map(e -> new LimitOrder(OrderType.BID, e.getValue(), pair, null, null, e.getKey())).collect(Collectors.toList());
    List<LimitOrder> asks = ob.asks.entrySet().stream().map(e -> new LimitOrder(OrderType.ASK, e.getValue(), pair, null, null, e.getKey())).collect(Collectors.toList());
    return new OrderBook(null, asks, bids);
  }

  @Override
  public Ticker getTicker(CurrencyPair pair, Object... args) throws IOException {
    BinanceTicker24h ticker24h = binance.ticker24h(BinanceAdapters.toSymbol(pair));
    return new Ticker.Builder()
        .currencyPair(pair)
        .ask(ticker24h.askPrice)
        .bid(ticker24h.bidPrice)
        .last(ticker24h.lastPrice)
        .high(ticker24h.highPrice)
        .low(ticker24h.lowPrice)
        .volume(ticker24h.volume)
        .vwap(ticker24h.weightedAvgPrice)
        .build();
  }

  /**
   * optional parameters provided in the args array:
   * <p><ul>
   * <li>0: Long fromId    optional, ID to get aggregate trades from INCLUSIVE.
   * <li>1: Long startTime optional, Timestamp in ms to get aggregate trades from INCLUSIVE.
   * <li>2: Long endTime   optional, Timestamp in ms to get aggregate trades until INCLUSIVE.
   * <li>3: Integer limit     optional, Default 500; max 500.
   * </ul><p/>
   */
  @Override
  public Trades getTrades(CurrencyPair pair, Object... args) throws IOException {
    Long fromId = null;
    Long startTime = null;
    Long endTime = null;
    Integer limit = null;

    try {
      fromId = Long.valueOf(args[0].toString());
    } catch (Throwable ignored) {
    }
    try {
      startTime = Long.valueOf(args[1].toString());
    } catch (Throwable ignored) {
    }
    try {
      endTime = Long.valueOf(args[2].toString());
    } catch (Throwable ignored) {
    }
    try {
      limit = Integer.valueOf(args[3].toString());
    } catch (Throwable ignored) {
    }

    List<BinanceAggTrades> aggTrades = binance.aggTrades(BinanceAdapters.toSymbol(pair), fromId, startTime, endTime, limit);
    List<Trade> trades = aggTrades.stream()
        .map(at -> new Trade(BinanceAdapters.convertType(at.buyerMaker), at.quantity, pair, at.price, at.getTimestamp(), Long.toString(at.aggregateTradeId)))
        .collect(Collectors.toList());
    return new Trades(trades, TradeSortType.SortByTimestamp);
  }

}