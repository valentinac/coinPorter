package com.qidiancamp.api.huobi;

import com.qidiancamp.currency.Currency;
import com.qidiancamp.currency.CurrencyPair;
import com.qidiancamp.dto.Order;
import com.qidiancamp.dto.Order.OrderStatus;
import com.qidiancamp.dto.Order.OrderType;
import com.qidiancamp.dto.account.Balance;
import com.qidiancamp.dto.account.Wallet;
import com.qidiancamp.dto.marketdata.Ticker;
import com.qidiancamp.dto.meta.CurrencyMetaData;
import com.qidiancamp.dto.meta.CurrencyPairMetaData;
import com.qidiancamp.dto.meta.ExchangeMetaData;
import com.qidiancamp.dto.trade.LimitOrder;
import com.qidiancamp.dto.trade.MarketOrder;
import com.qidiancamp.dto.trade.OpenOrders;
import com.qidiancamp.api.huobi.dto.account.HuobiBalanceRecord;
import com.qidiancamp.api.huobi.dto.account.HuobiBalanceSum;
import com.qidiancamp.api.huobi.dto.marketdata.HuobiAsset;
import com.qidiancamp.api.huobi.dto.marketdata.HuobiAssetPair;
import com.qidiancamp.api.huobi.dto.marketdata.HuobiTicker;
import com.qidiancamp.api.huobi.dto.trade.HuobiOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HuobiAdapters {

  public static Ticker adaptTicker(HuobiTicker huobiTicker, CurrencyPair currencyPair) {
    Ticker.Builder builder = new Ticker.Builder();
    builder.open(huobiTicker.getOpen());
    builder.ask(huobiTicker.getAsk().getPrice());
    builder.bid(huobiTicker.getBid().getPrice());
    builder.last(huobiTicker.getClose());
    builder.high(huobiTicker.getHigh());
    builder.low(huobiTicker.getLow());
    builder.volume(huobiTicker.getVol());
    builder.timestamp(huobiTicker.getTs());
    builder.currencyPair(currencyPair);
    return builder.build();
  }

  static ExchangeMetaData adaptToExchangeMetaData(
      HuobiAssetPair[] assetPairs, HuobiAsset[] assets) {
    HuobiUtils.setHuobiAssets(assets);
    HuobiUtils.setHuobiAssetPairs(assetPairs);

    Map<CurrencyPair, CurrencyPairMetaData> pairs = new HashMap<>();
    for (HuobiAssetPair pair : assetPairs) {
      pairs.put(adaptCurrencyPair(pair.getKey()), adaptPair(pair));
    }

    Map<Currency, CurrencyMetaData> currencies = new HashMap<>();
    for (HuobiAsset asset : assets) {
      Currency currency = adaptCurrency(asset.getAsset());
      currencies.put(currency, new CurrencyMetaData(0, null));
    }

    return new ExchangeMetaData(pairs, currencies, null, null, false);
  }

  private static CurrencyPair adaptCurrencyPair(String currencyPair) {
    return HuobiUtils.translateHuobiCurrencyPair(currencyPair);
  }

  private static CurrencyPairMetaData adaptPair(HuobiAssetPair pair) {
    return new CurrencyPairMetaData(null, null, null, new Integer(pair.getPricePrecision()));
  }

  private static Currency adaptCurrency(String currency) {
    return HuobiUtils.translateHuobiCurrencyCode(currency);
  }

  public static Wallet adaptWallet(Map<String, HuobiBalanceSum> huobiWallet) {
    List<Balance> balances = new ArrayList<>(huobiWallet.size());
    for (Map.Entry<String, HuobiBalanceSum> record : huobiWallet.entrySet()) {
      Currency currency = adaptCurrency(record.getKey());
      Balance balance =
          new Balance(
              currency,
              record.getValue().getTotal(),
              record.getValue().getAvailable(),
              record.getValue().getFrozen());
      balances.add(balance);
    }
    return new Wallet(balances);
  }

  public static Map<String, HuobiBalanceSum> adaptBalance(HuobiBalanceRecord[] huobiBalance) {
    Map<String, HuobiBalanceSum> map = new HashMap<>();
    for (HuobiBalanceRecord record : huobiBalance) {
      HuobiBalanceSum sum = map.get(record.getCurrency());
      if (sum == null) {
        sum = new HuobiBalanceSum();
        map.put(record.getCurrency(), sum);
      }
      if (record.getType().equals("trade")) {
        sum.setAvailable(record.getBalance());
      } else if (record.getType().equals("frozen")) {
        sum.setFrozen(record.getBalance());
      }
    }
    return map;
  }

  public static OpenOrders adaptOpenOrders(HuobiOrder[] openOrders) {
    List<LimitOrder> limitOrders = new ArrayList<>();
    for (HuobiOrder openOrder : openOrders) {
      if (openOrder.isLimit()) {
        limitOrders.add((LimitOrder) adaptOrder(openOrder));
      }
    }
    return new OpenOrders(limitOrders);
  }

  private static Order adaptOrder(HuobiOrder openOrder) {
    Order order = null;
    OrderType orderType = adaptOrderType(openOrder.getType());
    CurrencyPair currencyPair = adaptCurrencyPair(openOrder.getSymbol());
    if (openOrder.isMarket()) {
      order =
          new MarketOrder(
              orderType,
              openOrder.getAmount(),
              currencyPair,
              String.valueOf(openOrder.getId()),
              openOrder.getCreatedAt(),
              null,
              openOrder.getFieldAmount(),
              openOrder.getFieldFees(),
              null);
    }
    if (openOrder.isLimit()) {
      order =
          new LimitOrder(
              orderType,
              openOrder.getAmount(),
              openOrder.getFieldAmount(),
              currencyPair,
              String.valueOf(openOrder.getId()),
              openOrder.getCreatedAt(),
              openOrder.getPrice());
    }
    if (order != null) {
      order.setOrderStatus(adaptOrderStatus(openOrder.getState()));
    }
    return order;
  }

  private static OrderStatus adaptOrderStatus(String huobiStatus) {
    OrderStatus result = OrderStatus.UNKNOWN;
    switch (huobiStatus) {
      case "pre-submitted":
        result = OrderStatus.PENDING_NEW;
        break;
      case "submitting":
        result = OrderStatus.PENDING_NEW;
        break;
      case "submitted":
        result = OrderStatus.NEW;
        break;
      case "partial-filled":
        result = OrderStatus.PARTIALLY_FILLED;
        break;
      case "partial-canceled":
        result = OrderStatus.PARTIALLY_CANCELED;
        break;
      case "filled":
        result = OrderStatus.FILLED;
        break;
      case "canceled":
        result = OrderStatus.CANCELED;
        break;
    }
    return result;
  }

  private static OrderType adaptOrderType(String orderType) {
    if (orderType.startsWith("buy")) {
      return OrderType.BID;
    }
    if (orderType.startsWith("sell")) {
      return OrderType.ASK;
    }
    return null;
  }

  public static List<Order> adaptOrders(List<HuobiOrder> huobiOrders) {
    List<Order> orders = new ArrayList<>();
    for (HuobiOrder order : huobiOrders) {
      orders.add(adaptOrder(order));
    }
    return orders;
  }
}
