package com.qidiancamp.api.gate.service;


import com.qidiancamp.Exchange;
import com.qidiancamp.api.gate.GateioAdapters;
import com.qidiancamp.api.gate.dto.marketdata.GateioDepth;
import com.qidiancamp.api.gate.dto.marketdata.GateioTicker;
import com.qidiancamp.api.gate.dto.marketdata.GateioTradeHistory;
import com.qidiancamp.currency.CurrencyPair;
import com.qidiancamp.dto.marketdata.OrderBook;
import com.qidiancamp.dto.marketdata.Ticker;
import com.qidiancamp.dto.marketdata.Trades;
import com.qidiancamp.service.marketdata.MarketDataService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GateioMarketDataService extends GateioMarketDataServiceRaw implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public GateioMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    GateioTicker ticker = super.getBTERTicker(currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());

    return GateioAdapters.adaptTicker(currencyPair, ticker);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    GateioDepth gateioDepth = super.getBTEROrderBook(currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());

    return GateioAdapters.adaptOrderBook(gateioDepth, currencyPair);
  }

  public Map<CurrencyPair, OrderBook> getOrderBooks() throws IOException {

    Map<CurrencyPair, GateioDepth> gateioDepths = super.getGateioDepths();
    Map<CurrencyPair, OrderBook> orderBooks = new HashMap<>(gateioDepths.size());

    gateioDepths.forEach((currencyPair, gateioDepth) -> {
      OrderBook orderBook = GateioAdapters.adaptOrderBook(gateioDepth, currencyPair);
      orderBooks.put(currencyPair, orderBook);
    });

    return orderBooks;
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    GateioTradeHistory tradeHistory = (args != null && args.length > 0 && args[0] != null && args[0] instanceof String)
        ? super.getBTERTradeHistorySince(currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode(), (String) args[0])
        : super.getBTERTradeHistory(currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());

    return GateioAdapters.adaptTrades(tradeHistory, currencyPair);
  }

}
