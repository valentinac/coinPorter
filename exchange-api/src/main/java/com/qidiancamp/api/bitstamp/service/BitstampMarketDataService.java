package com.qidiancamp.api.bitstamp.service;

import java.io.IOException;
import com.qidiancamp.Exchange;
import com.qidiancamp.api.bitstamp.BitstampAdapters;
import com.qidiancamp.currency.CurrencyPair;
import com.qidiancamp.dto.marketdata.OrderBook;
import com.qidiancamp.dto.marketdata.Ticker;
import com.qidiancamp.dto.marketdata.Trades;
import com.qidiancamp.service.marketdata.MarketDataService;
import org.springframework.stereotype.Service;

/** @author Matija Mazi */
@Service
public class BitstampMarketDataService extends BitstampMarketDataServiceRaw
    implements MarketDataService {

  public BitstampMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    return BitstampAdapters.adaptTicker(getBitstampTicker(currencyPair), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    return BitstampAdapters.adaptOrderBook(getBitstampOrderBook(currencyPair), currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    BitstampTime time = args.length > 0 ? (BitstampTime) args[0] : null;
    return BitstampAdapters.adaptTrades(getTransactions(currencyPair, time), currencyPair);
  }
}
