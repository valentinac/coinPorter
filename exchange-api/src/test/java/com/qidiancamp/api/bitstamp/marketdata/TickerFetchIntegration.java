package com.qidiancamp.api.bitstamp.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.qidiancamp.api.bitstamp.BitstampExchange;
import org.junit.Test;
import com.qidiancamp.Exchange;
import com.qidiancamp.ExchangeFactory;
import com.qidiancamp.currency.CurrencyPair;
import com.qidiancamp.dto.marketdata.Ticker;
import com.qidiancamp.service.marketdata.MarketDataService;

/** @author timmolter */
public class TickerFetchIntegration {

  @Test
  public void tickerFetchTest() throws Exception {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(BitstampExchange.class.getName());
    MarketDataService marketDataService = exchange.getMarketDataService();
    Ticker ticker = marketDataService.getTicker(new CurrencyPair("BTC", "USD"));
    System.out.println(ticker.toString());
    assertThat(ticker).isNotNull();
  }
}
