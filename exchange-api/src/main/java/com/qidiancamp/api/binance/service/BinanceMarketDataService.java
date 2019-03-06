package com.qidiancamp.api.binance.service;

import com.qidiancamp.Exchange;
import com.qidiancamp.api.binance.BinanceAdapters;
import com.qidiancamp.api.binance.BinanceErrorAdapter;
import com.qidiancamp.api.binance.dto.BinanceException;
import com.qidiancamp.api.binance.dto.marketdata.BinanceAggTrades;
import com.qidiancamp.api.binance.dto.marketdata.BinanceOrderbook;
import com.qidiancamp.api.binance.dto.marketdata.BinanceTicker24h;
import com.qidiancamp.currency.CurrencyPair;
import com.qidiancamp.dto.Order;
import com.qidiancamp.dto.marketdata.OrderBook;
import com.qidiancamp.dto.marketdata.Ticker;
import com.qidiancamp.dto.marketdata.Trade;
import com.qidiancamp.dto.marketdata.Trades;
import com.qidiancamp.dto.trade.LimitOrder;
import com.qidiancamp.exceptions.ExchangeException;
import com.qidiancamp.service.marketdata.MarketDataService;
import com.qidiancamp.service.marketdata.params.Params;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BinanceMarketDataService extends BinanceMarketDataServiceRaw
    implements MarketDataService {

  public BinanceMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair pair, Object... args) throws IOException {
    try {
      int limitDepth = 100;

      if (args != null && args.length == 1) {
        Object arg0 = args[0];
        if (!(arg0 instanceof Integer)) {
          throw new ExchangeException("Argument 0 must be an Integer!");
        } else {
          limitDepth = (Integer) arg0;
        }
      }
      BinanceOrderbook binanceOrderbook = getBinanceOrderbook(pair, limitDepth);
      return convertOrderBook(binanceOrderbook, pair);
    } catch (BinanceException e) {
      throw BinanceErrorAdapter.adapt(e);
    }
  }

  public static OrderBook convertOrderBook(BinanceOrderbook ob, CurrencyPair pair) {
    List<LimitOrder> bids =
        ob.bids.entrySet().stream()
            .map(e -> new LimitOrder(Order.OrderType.BID, e.getValue(), pair, null, null, e.getKey()))
            .collect(Collectors.toList());
    List<LimitOrder> asks =
        ob.asks.entrySet().stream()
            .map(e -> new LimitOrder(Order.OrderType.ASK, e.getValue(), pair, null, null, e.getKey()))
            .collect(Collectors.toList());
    return new OrderBook(null, asks, bids);
  }

  @Override
  public Ticker getTicker(CurrencyPair pair, Object... args) throws IOException {
    try {
      return ticker24h(pair).toTicker();
    } catch (BinanceException e) {
      throw BinanceErrorAdapter.adapt(e);
    }
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {
    try {
      return ticker24h().stream().map(BinanceTicker24h::toTicker).collect(Collectors.toList());
    } catch (BinanceException e) {
      throw BinanceErrorAdapter.adapt(e);
    }
  }

  /**
   * optional parameters provided in the args array:
   *
   * <ul>
   *   <li>0: Long fromId optional, ID to get aggregate trades from INCLUSIVE.
   *   <li>1: Long startTime optional, Timestamp in ms to get aggregate trades from INCLUSIVE.
   *   <li>2: Long endTime optional, Timestamp in ms to get aggregate trades until INCLUSIVE.
   *   <li>3: Integer limit optional, Default 500; max 500.
   * </ul>
   *
   * <p>
   */
  @Override
  public Trades getTrades(CurrencyPair pair, Object... args) throws IOException {
    try {
      Long fromId = tradesArgument(args, 0, Long::valueOf);
      Long startTime = tradesArgument(args, 1, Long::valueOf);
      Long endTime = tradesArgument(args, 2, Long::valueOf);
      Integer limit = tradesArgument(args, 3, Integer::valueOf);
      List<BinanceAggTrades> aggTrades =
          binance.aggTrades(BinanceAdapters.toSymbol(pair), fromId, startTime, endTime, limit);
      List<Trade> trades =
          aggTrades.stream()
              .map(
                  at ->
                      new Trade(
                          BinanceAdapters.convertType(at.buyerMaker),
                          at.quantity,
                          pair,
                          at.price,
                          at.getTimestamp(),
                          Long.toString(at.aggregateTradeId)))
              .collect(Collectors.toList());
      return new Trades(trades, Trades.TradeSortType.SortByTimestamp);
    } catch (BinanceException e) {
      throw BinanceErrorAdapter.adapt(e);
    }
  }

  private <T extends Number> T tradesArgument(
      Object[] args, int index, Function<String, T> converter) {
    if (index >= args.length) {
      return null;
    }
    Object arg = args[index];
    if (arg == null) {
      return null;
    }
    String argStr = arg.toString();
    try {
      return converter.apply(argStr);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(
          "Argument on index " + index + " is not a number: " + argStr, e);
    }
  }
}
