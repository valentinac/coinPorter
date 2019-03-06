package com.qidiancamp.api.coinone;

import com.qidiancamp.BaseExchange;
import com.qidiancamp.Exchange;
import com.qidiancamp.ExchangeSpecification;
import com.qidiancamp.api.coinone.service.CoinoneAccountService;
import com.qidiancamp.api.coinone.service.CoinoneMarketDataService;
import com.qidiancamp.api.coinone.service.CoinoneTradeService;
import com.qidiancamp.common.utils.nonce.CurrentTimeNonceFactory;
import com.qidiancamp.exceptions.ExchangeException;
import si.mazi.rescu.SynchronizedValueFactory;

import java.io.IOException;

public class CoinoneExchange extends BaseExchange implements Exchange {
  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  public static enum period {
    hour,
    day
  }

  @Override
  protected void initServices() {
    this.marketDataService = new CoinoneMarketDataService(this);
    this.accountService = new CoinoneAccountService(this);
    this.tradeService = new CoinoneTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.coinone.co.kr");
    exchangeSpecification.setHost("www.coinone.co.kr");
    exchangeSpecification.setExchangeName("Coinone");
    exchangeSpecification.setExchangeDescription("Coinone is a block chain exchange.");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }

  @Override
  public void remoteInit() throws IOException, ExchangeException {}
}
