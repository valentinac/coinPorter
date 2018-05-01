package com.qidiancamp.api.huobi;

import org.junit.Test;
import com.qidiancamp.Exchange;
import com.qidiancamp.ExchangeFactory;
import com.qidiancamp.currency.CurrencyPair;
import com.qidiancamp.dto.marketdata.Ticker;
import com.qidiancamp.service.marketdata.MarketDataService;

import java.util.Arrays;
public class HuobiPublicApiIntegration {

  @Test
  public void getTickerTest() throws Exception {
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(HuobiExchange.class.getName());
    MarketDataService marketDataService = exchange.getMarketDataService();
    Ticker ticker = marketDataService.getTicker(new CurrencyPair("BTC", "USDT"));
    System.out.println(ticker.toString());
//    assertThat(ticker).isNotNull();
  }

  @Test
  public void getExchangeSymbolsTest() {
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(HuobiExchange.class.getName());
    System.out.println(Arrays.toString(exchange.getExchangeSymbols().toArray()));
  }
}
