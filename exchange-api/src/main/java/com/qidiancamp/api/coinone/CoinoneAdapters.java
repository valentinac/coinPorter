package com.qidiancamp.api.coinone;

import com.qidiancamp.api.coinone.dto.CoinoneException;
import com.qidiancamp.api.coinone.dto.account.CoinoneBalancesResponse;
import com.qidiancamp.api.coinone.dto.marketdata.*;
import com.qidiancamp.api.coinone.dto.trade.CoinoneOrderInfo;
import com.qidiancamp.api.coinone.dto.trade.CoinoneOrderInfoResponse;
import com.qidiancamp.common.utils.DateUtils;
import com.qidiancamp.currency.CurrencyPair;
import com.qidiancamp.dto.Order;
import com.qidiancamp.dto.account.Balance;
import com.qidiancamp.dto.account.Wallet;
import com.qidiancamp.dto.marketdata.OrderBook;
import com.qidiancamp.dto.marketdata.Ticker;
import com.qidiancamp.dto.marketdata.Trade;
import com.qidiancamp.dto.marketdata.Trades;
import com.qidiancamp.dto.trade.LimitOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;

public final class CoinoneAdapters {

  public static final Logger log = LoggerFactory.getLogger(CoinoneAdapters.class);

  private CoinoneAdapters() {}

  public static OrderBook adaptOrderBook(
          CoinoneOrderBook coinoneOrderBook, CurrencyPair currencyPair) {
    if (!"0".equals(coinoneOrderBook.getErrorCode())) {
      throw new CoinoneException(coinoneOrderBook.getResult());
    }

    List<LimitOrder> asks =
        adaptMarketOrderToLimitOrder(coinoneOrderBook.getAsks(), Order.OrderType.ASK, currencyPair);
    List<LimitOrder> bids =
        adaptMarketOrderToLimitOrder(coinoneOrderBook.getBids(), Order.OrderType.BID, currencyPair);

    return new OrderBook(
        DateUtils.fromMillisUtc(Long.valueOf(coinoneOrderBook.getTimestamp()) * 1000), asks, bids);
  }

  private static List<LimitOrder> adaptMarketOrderToLimitOrder(
          CoinoneOrderBookData[] coinoneOrders, Order.OrderType orderType, CurrencyPair currencyPair) {

    List<LimitOrder> orders = new ArrayList<>(coinoneOrders.length);
    for (int i = 0; i < coinoneOrders.length; i++) {
      CoinoneOrderBookData coinoneOrder = coinoneOrders[i];
      BigDecimal price = coinoneOrder.getPrice();
      BigDecimal amount = coinoneOrder.getQty();
      LimitOrder limitOrder = new LimitOrder(orderType, amount, currencyPair, null, null, price);
      orders.add(limitOrder);
    }

    return orders;
  }

  public static Order adaptOrderInfo(CoinoneOrderInfoResponse coinoneOrderInfoResponse) {
    ArrayList<Order> orders = new ArrayList<Order>();
    Order.OrderStatus status = Order.OrderStatus.NEW;
    if (coinoneOrderInfoResponse.getStatus().equals("live")) status = Order.OrderStatus.NEW;
    else if (coinoneOrderInfoResponse.getStatus().equals("filled")) status = Order.OrderStatus.FILLED;
    else if (coinoneOrderInfoResponse.getStatus().equals("partially_filled"))
      status = Order.OrderStatus.PARTIALLY_FILLED;
    else status = Order.OrderStatus.CANCELED;
    CoinoneOrderInfo orderInfo = coinoneOrderInfoResponse.getInfo();
    Order.OrderType type = orderInfo.getType().equals("ask") ? Order.OrderType.ASK : Order.OrderType.BID;
    BigDecimal originalAmount = orderInfo.getQty();
    CurrencyPair currencyPair =
        new CurrencyPair(new Currency(orderInfo.getCurrency().toUpperCase()), Currency.KRW);
    String orderId = orderInfo.getOrderId();
    BigDecimal cumulativeAmount = orderInfo.getQty().subtract(orderInfo.getRemainQty());
    BigDecimal price = orderInfo.getPrice();
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(orderInfo.getTimestamp());
    LimitOrder order =
        new LimitOrder(
            type,
            originalAmount,
            currencyPair,
            orderId,
            cal.getTime(),
            price,
            price,
            cumulativeAmount,
            orderInfo.getFee(),
            status);
    return order;
  }

  public static Wallet adaptWallet(CoinoneBalancesResponse coninoneResponse) {
    if (!"0".equals(coninoneResponse.getErrorCode())) {
      throw new CoinoneException(coninoneResponse.getResult());
    }
    List<Balance> balances = new ArrayList<>();
    balances.add(
        new Balance(
            Currency.getInstance("KRW"),
            coninoneResponse.getKrw().getBalance(),
            coninoneResponse.getKrw().getAvail()));
    balances.add(
        new Balance(
            Currency.getInstance("BCH"),
            coninoneResponse.getBch().getBalance(),
            coninoneResponse.getBch().getAvail()));
    balances.add(
        new Balance(
            Currency.getInstance("BTG"),
            coninoneResponse.getBtg().getBalance(),
            coninoneResponse.getBtg().getAvail()));
    balances.add(
        new Balance(
            Currency.getInstance("IOTA"),
            coninoneResponse.getIota().getBalance(),
            coninoneResponse.getIota().getAvail()));
    balances.add(
        new Balance(
            Currency.getInstance("EOS"),
            coninoneResponse.getEos().getBalance(),
            coninoneResponse.getEos().getAvail()));
    balances.add(
        new Balance(
            Currency.getInstance("OMG"),
            coninoneResponse.getOmg().getBalance(),
            coninoneResponse.getOmg().getAvail()));
    balances.add(
        new Balance(
            Currency.getInstance("LTC"),
            coninoneResponse.getLtc().getBalance(),
            coninoneResponse.getLtc().getAvail()));
    balances.add(
        new Balance(
            Currency.getInstance("BTC"),
            coninoneResponse.getBtc().getBalance(),
            coninoneResponse.getBtc().getAvail()));
    balances.add(
        new Balance(
            Currency.getInstance("ETC"),
            coninoneResponse.getEtc().getBalance(),
            coninoneResponse.getEtc().getAvail()));
    balances.add(
        new Balance(
            Currency.getInstance("ETH"),
            coninoneResponse.getEth().getBalance(),
            coninoneResponse.getEth().getAvail()));
    balances.add(
        new Balance(
            Currency.getInstance("QTUM"),
            coninoneResponse.getQtum().getBalance(),
            coninoneResponse.getQtum().getAvail()));
    balances.add(
        new Balance(
            Currency.getInstance("XRP"),
            coninoneResponse.getXrp().getBalance(),
            coninoneResponse.getXrp().getAvail()));
    return new Wallet(balances);
  }

  public static Ticker adaptTicker(CoinoneTicker ticker) {
    CurrencyPair currencyPair =
        new CurrencyPair(Currency.getInstance(ticker.getCurrency()), Currency.KRW);
    final Date date = DateUtils.fromMillisUtc(Long.valueOf(ticker.getTimestamp()) * 1000);
    return new Ticker.Builder()
        .currencyPair(currencyPair)
        .high(ticker.getHigh())
        .low(ticker.getLow())
        .last(ticker.getLast())
        .volume(ticker.getVolume())
        .open(ticker.getFirst())
        .timestamp(date)
        .build();
  }

  public static Trades adaptTrades(CoinoneTrades trades, CurrencyPair currencyPair) {
    if (!"0".equals(trades.getErrorCode())) {
      throw new CoinoneException(trades.getResult());
    }
    List<Trade> tradeList = new ArrayList<>(trades.getCompleteOrders().length);
    for (CoinoneTradeData trade : trades.getCompleteOrders()) {
      tradeList.add(adaptTrade(trade, currencyPair));
    }
    return new Trades(tradeList, 0, Trades.TradeSortType.SortByTimestamp);
  }

  private static Trade adaptTrade(CoinoneTradeData trade, CurrencyPair currencyPair) {
    return new Trade(
        null,
        trade.getQty(),
        currencyPair,
        trade.getPrice(),
        DateUtils.fromMillisUtc(Long.valueOf(trade.getTimestamp()) * 1000),
        "");
  }
}
