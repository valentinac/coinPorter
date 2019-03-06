package com.qidiancamp.api.coinone.service;

import com.qidiancamp.Exchange;
import com.qidiancamp.api.coinone.CoinoneAdapters;
import com.qidiancamp.api.coinone.CoinoneExchange;
import com.qidiancamp.currency.CurrencyPair;
import com.qidiancamp.dto.marketdata.OrderBook;
import com.qidiancamp.dto.marketdata.Ticker;
import com.qidiancamp.dto.marketdata.Trades;
import com.qidiancamp.exceptions.ExchangeException;
import com.qidiancamp.exceptions.NotAvailableFromExchangeException;
import com.qidiancamp.exceptions.NotYetImplementedForExchangeException;
import com.qidiancamp.service.marketdata.MarketDataService;

import java.io.IOException;

/** @author interwater */
public class CoinoneMarketDataService extends CoinoneMarketDataServiceRaw
    implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CoinoneMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    return CoinoneAdapters.adaptTicker(super.getTicker(currencyPair));
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args)
      throws ExchangeException, NotAvailableFromExchangeException,
          NotYetImplementedForExchangeException, IOException {
    return CoinoneAdapters.adaptOrderBook(getCoinoneOrderBook(currencyPair), currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args)
      throws ExchangeException, NotAvailableFromExchangeException,
          NotYetImplementedForExchangeException, IOException {
    String period = "hour";
    if (args[0] != null) {
      try {
        period = CoinoneExchange.period.valueOf(args[0].toString()).name();
      } catch (IllegalArgumentException e) {
      }
    }
    return CoinoneAdapters.adaptTrades(super.getTrades(currencyPair, period), currencyPair);
  }
}
