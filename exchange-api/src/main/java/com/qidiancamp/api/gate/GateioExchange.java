package com.qidiancamp.api.gate;


import com.qidiancamp.BaseExchange;
import com.qidiancamp.Exchange;
import com.qidiancamp.ExchangeSpecification;
import com.qidiancamp.api.gate.dto.marketdata.GateioMarketInfoWrapper;
import com.qidiancamp.api.gate.service.GateioAccountService;
import com.qidiancamp.api.gate.service.GateioMarketDataService;
import com.qidiancamp.api.gate.service.GateioMarketDataServiceRaw;
import com.qidiancamp.api.gate.service.GateioTradeService;
import com.qidiancamp.currency.CurrencyPair;
import com.qidiancamp.utils.nonce.AtomicLongIncrementalTime2013NonceFactory;
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

    Map<CurrencyPair, GateioMarketInfoWrapper.GateioMarketInfo> currencyPair2BTERMarketInfoMap = ((GateioMarketDataServiceRaw) marketDataService).getBTERMarketInfo();
    exchangeMetaData = GateioAdapters.adaptToExchangeMetaData(currencyPair2BTERMarketInfoMap);
  }
}
