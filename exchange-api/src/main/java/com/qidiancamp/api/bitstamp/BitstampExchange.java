package com.qidiancamp.api.bitstamp;


import com.qidiancamp.BaseExchange;
import com.qidiancamp.Exchange;
import com.qidiancamp.ExchangeSpecification;
import com.qidiancamp.api.bitstamp.service.BitstampAccountService;
import com.qidiancamp.api.bitstamp.service.BitstampMarketDataService;
import com.qidiancamp.api.bitstamp.service.BitstampTradeService;
import com.qidiancamp.utils.nonce.CurrentTimeNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

/**
 * @author Matija Mazi
 */
public class BitstampExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Override
  protected void initServices() {

    this.marketDataService = new BitstampMarketDataService(this);
    this.tradeService = new BitstampTradeService(this);
    this.accountService = new BitstampAccountService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://www.bitstamp.net");
    exchangeSpecification.setHost("www.bitstamp.net");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Bitstamp");
    exchangeSpecification.setExchangeDescription("Bitstamp is a Bitcoin exchange registered in Slovenia.");
    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }
}
