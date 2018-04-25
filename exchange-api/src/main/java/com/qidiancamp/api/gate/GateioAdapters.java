package com.qidiancamp.api.gate;

import com.qidiancamp.api.gate.dto.GateioOrderType;
import com.qidiancamp.api.gate.dto.account.GateioFunds;
import com.qidiancamp.api.gate.dto.marketdata.*;
import com.qidiancamp.api.gate.dto.trade.GateioOpenOrder;
import com.qidiancamp.api.gate.dto.trade.GateioOpenOrders;
import com.qidiancamp.api.gate.dto.trade.GateioTrade;
import com.qidiancamp.currency.CurrencyPair;
import com.qidiancamp.dto.Order;
import com.qidiancamp.dto.account.Balance;
import com.qidiancamp.dto.account.Wallet;
import com.qidiancamp.dto.marketdata.OrderBook;
import com.qidiancamp.dto.marketdata.Ticker;
import com.qidiancamp.dto.marketdata.Trade;
import com.qidiancamp.dto.marketdata.Trades;
import com.qidiancamp.dto.meta.CurrencyPairMetaData;
import com.qidiancamp.dto.meta.ExchangeMetaData;
import com.qidiancamp.dto.trade.LimitOrder;
import com.qidiancamp.dto.trade.OpenOrders;
import com.qidiancamp.dto.trade.UserTrade;
import com.qidiancamp.dto.trade.UserTrades;
import com.qidiancamp.utils.DateUtils;
import com.qidiancamp.currency.Currency;

import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;

/**
 * Various adapters for converting from Bter DTOs to XChange DTOs
 */
public final class GateioAdapters {

  /**
   * private Constructor
   */
  private GateioAdapters() {

  }

  public static CurrencyPair adaptCurrencyPair(String pair) {

    final String[] currencies = pair.toUpperCase().split("_");
    return new CurrencyPair(currencies[0], currencies[1]);
  }

  public static Ticker adaptTicker(CurrencyPair currencyPair, GateioTicker gateioTicker) {

    BigDecimal ask = gateioTicker.getLowestAsk();
    BigDecimal bid = gateioTicker.getHighestBid();
    BigDecimal last = gateioTicker.getLast();
    BigDecimal low = gateioTicker.getLow24hr();
    BigDecimal high = gateioTicker.getHigh24hr();
    // Looks like gate.io vocabulary is inverted...
    BigDecimal baseVolume = gateioTicker.getQuoteVolume();
    BigDecimal quoteVolume = gateioTicker.getBaseVolume();

    return new Ticker.Builder().currencyPair(currencyPair).ask(ask).bid(bid).last(last).low(low).high(high).volume(baseVolume).quoteVolume(quoteVolume).build();
  }

  public static LimitOrder adaptOrder(GateioPublicOrder order, CurrencyPair currencyPair, Order.OrderType orderType) {

    return new LimitOrder(orderType, order.getAmount(), currencyPair, "", null, order.getPrice());
  }

  public static List<LimitOrder> adaptOrders(List<GateioPublicOrder> orders, CurrencyPair currencyPair, Order.OrderType orderType) {

    List<LimitOrder> limitOrders = new ArrayList<>();

    for (GateioPublicOrder bterOrder : orders) {
      limitOrders.add(adaptOrder(bterOrder, currencyPair, orderType));
    }

    return limitOrders;
  }

  public static OrderBook adaptOrderBook(GateioDepth depth, CurrencyPair currencyPair) {

    List<LimitOrder> asks = GateioAdapters.adaptOrders(depth.getAsks(), currencyPair, Order.OrderType.ASK);
    Collections.reverse(asks);
    List<LimitOrder> bids = GateioAdapters.adaptOrders(depth.getBids(), currencyPair, Order.OrderType.BID);

    return new OrderBook(null, asks, bids);
  }

  public static LimitOrder adaptOrder(GateioOpenOrder order, Collection<CurrencyPair> currencyPairs) {

    String[] currencyPairSplit = order.getCurrencyPair().split("_");
    CurrencyPair currencyPair = new CurrencyPair(currencyPairSplit[0], currencyPairSplit[1]);
    return new LimitOrder(order.getType().equals("sell") ? Order.OrderType.ASK : Order.OrderType.BID, order.getAmount(), currencyPair, order.getOrderNumber(),
        null, order.getRate());
  }

  public static OpenOrders adaptOpenOrders(GateioOpenOrders openOrders, Collection<CurrencyPair> currencyPairs) {

    List<LimitOrder> adaptedOrders = new ArrayList<>();
    for (GateioOpenOrder openOrder : openOrders.getOrders()) {
      adaptedOrders.add(adaptOrder(openOrder, currencyPairs));
    }

    return new OpenOrders(adaptedOrders);
  }

  public static Order.OrderType adaptOrderType(GateioOrderType cryptoTradeOrderType) {

    return (cryptoTradeOrderType.equals(GateioOrderType.BUY)) ? Order.OrderType.BID : Order.OrderType.ASK;
  }

  public static Trade adaptTrade(GateioTradeHistory.GateioPublicTrade trade, CurrencyPair currencyPair) {

    Order.OrderType orderType = adaptOrderType(trade.getType());
    Date timestamp = DateUtils.fromMillisUtc(trade.getDate() * 1000);

    return new Trade(orderType, trade.getAmount(), currencyPair, trade.getPrice(), timestamp, trade.getTradeId());
  }

  public static Trades adaptTrades(GateioTradeHistory tradeHistory, CurrencyPair currencyPair) {

    List<Trade> tradeList = new ArrayList<>();
    long lastTradeId = 0;
    for (GateioTradeHistory.GateioPublicTrade trade : tradeHistory.getTrades()) {
      String tradeIdString = trade.getTradeId();
      if (!tradeIdString.isEmpty()) {
        long tradeId = Long.valueOf(tradeIdString);
        if (tradeId > lastTradeId) {
          lastTradeId = tradeId;
        }
      }
      Trade adaptedTrade = adaptTrade(trade, currencyPair);
      tradeList.add(adaptedTrade);
    }

    return new Trades(tradeList, lastTradeId, Trades.TradeSortType.SortByTimestamp);
  }

  public static Wallet adaptWallet(GateioFunds bterAccountInfo) {

    List<Balance> balances = new ArrayList<>();
    for (Entry<String, BigDecimal> funds : bterAccountInfo.getAvailableFunds().entrySet()) {
      Currency currency = Currency.getInstance(funds.getKey().toUpperCase());
      BigDecimal amount = funds.getValue();
      BigDecimal locked = bterAccountInfo.getLockedFunds().get(currency.toString());

      balances.add(new Balance(currency, null, amount, locked == null ? BigDecimal.ZERO : locked));
    }
    for (Entry<String, BigDecimal> funds : bterAccountInfo.getLockedFunds().entrySet()) {
      Currency currency = Currency.getInstance(funds.getKey().toUpperCase());
      if (balances.stream().noneMatch(balance -> balance.getCurrency().equals(currency))) {
        BigDecimal amount = funds.getValue();
        balances.add(new Balance(currency, null, BigDecimal.ZERO, amount));
      }
    }

    return new Wallet(balances);
  }

  public static UserTrades adaptUserTrades(List<GateioTrade> userTrades) {

    List<UserTrade> trades = new ArrayList<>();
    for (GateioTrade userTrade : userTrades) {
      trades.add(adaptUserTrade(userTrade));
    }

    return new UserTrades(trades, Trades.TradeSortType.SortByTimestamp);
  }

  public static UserTrade adaptUserTrade(GateioTrade gateioTrade) {

    Order.OrderType orderType = adaptOrderType(gateioTrade.getType());
    Date timestamp = DateUtils.fromMillisUtc(gateioTrade.getTimeUnix() * 1000);
    CurrencyPair currencyPair = adaptCurrencyPair(gateioTrade.getPair());

    return new UserTrade(orderType, gateioTrade.getAmount(), currencyPair, gateioTrade.getRate(), timestamp, gateioTrade.getId(), null, null,
        (Currency) null);
  }

  public static ExchangeMetaData adaptToExchangeMetaData(Map<CurrencyPair, GateioMarketInfoWrapper.GateioMarketInfo> currencyPair2BTERMarketInfoMap) {

    Map<CurrencyPair, CurrencyPairMetaData> currencyPairs = new HashMap<>();

    for (Entry<CurrencyPair, GateioMarketInfoWrapper.GateioMarketInfo> entry : currencyPair2BTERMarketInfoMap.entrySet()) {

      CurrencyPair currencyPair = entry.getKey();
      GateioMarketInfoWrapper.GateioMarketInfo btermarketInfo = entry.getValue();

      CurrencyPairMetaData currencyPairMetaData = new CurrencyPairMetaData(btermarketInfo.getFee(), btermarketInfo.getMinAmount(), null,
          btermarketInfo.getDecimalPlaces());
      currencyPairs.put(currencyPair, currencyPairMetaData);
    }

    ExchangeMetaData exchangeMetaData = new ExchangeMetaData(currencyPairs, null, null, null, null);

    return exchangeMetaData;
  }

}
