package com.qidiancamp.api.binanace.marketdata;

import static org.assertj.core.api.Assertions.assertThat;
import com.qidiancamp.Exchange;
import com.qidiancamp.ExchangeFactory;
import com.qidiancamp.api.gate.GateioExchange;
import com.qidiancamp.currency.CurrencyPair;
import com.qidiancamp.dto.marketdata.Ticker;
import com.qidiancamp.service.marketdata.MarketDataService;
import org.junit.Test;

/** @author timmolter */
public class TickerFetchIntegration {

  @Test
  public void tickerFetchTest() throws Exception {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(GateioExchange.class.getName());
    MarketDataService marketDataService = exchange.getMarketDataService();
    Ticker ticker = marketDataService.getTicker(new CurrencyPair("BTC", "USDT"));
    System.out.println(ticker.toString());
    assertThat(ticker).isNotNull();
  }
}
