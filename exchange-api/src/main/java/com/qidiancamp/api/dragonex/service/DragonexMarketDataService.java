package com.qidiancamp.api.dragonex.service;


import com.qidiancamp.Exchange;
import com.qidiancamp.api.dragonex.dto.marketdata.Order;
import com.qidiancamp.currency.CurrencyPair;
import com.qidiancamp.dto.Order.OrderType;
import com.qidiancamp.dto.marketdata.OrderBook;
import com.qidiancamp.dto.marketdata.Ticker;
import com.qidiancamp.dto.trade.LimitOrder;
import com.qidiancamp.service.marketdata.MarketDataService;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class DragonexMarketDataService extends DragonexMarketDataServiceRaw
    implements MarketDataService {

  public DragonexMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair pair, Object... args) throws IOException {
    com.qidiancamp.api.dragonex.dto.marketdata.Ticker t =
        super.ticker(exchange.symbolId(pair)).get(0);
    return new Ticker.Builder()
        .currencyPair(pair)
        .volume(t.totalVolume)
        .quoteVolume(t.totalAmount)
        .high(t.maxPrice)
        .low(t.minPrice)
        .open(t.openPrice)
        .last(t.closePrice)
        .timestamp(new Date(t.timestamp * 1000))
        .build();
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair pair, Object... args) throws IOException {
    long symbolId = exchange.symbolId(pair);
    BiFunction<OrderType, Order, LimitOrder> f =
        (t, o) -> new LimitOrder(t, o.volume, pair, null, null, o.price);
    List<LimitOrder> bids =
        super.marketBuyOrders(symbolId).stream()
            .map(o -> f.apply(OrderType.BID, o))
            .collect(Collectors.toList());
    List<LimitOrder> asks =
        super.marketSellOrders(symbolId).stream()
            .map(o -> f.apply(OrderType.ASK, o))
            .collect(Collectors.toList());
    return new OrderBook(null, asks, bids);
  }
}
