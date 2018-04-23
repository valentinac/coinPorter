package com.qidiancamp.api.gate;


import com.qidiancamp.BaseExchange;
import com.qidiancamp.Exchange;
import si.mazi.rescu.SynchronizedValueFactory;

import java.io.IOException;
import java.util.Map;

public class GateioExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new AtomicLongIncrementalTime2013NonceFactory();

  @Override
  protected void initServices() {
    this.marketDataService = new GateioMarketDataService(this);
    this.accountService = new GateioAccountService(this);
    this.tradeService = new GateioTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://data.gate.io");
    exchangeSpecification.setHost("gate.io");
    exchangeSpecification.setExchangeName("Gateio");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }

  @Override
  public void remoteInit() throws IOException {

    Map<CurrencyPair, GateioMarketInfo> currencyPair2BTERMarketInfoMap = ((GateioMarketDataServiceRaw) marketDataService).getBTERMarketInfo();
    exchangeMetaData = GateioAdapters.adaptToExchangeMetaData(currencyPair2BTERMarketInfoMap);
  }
}
