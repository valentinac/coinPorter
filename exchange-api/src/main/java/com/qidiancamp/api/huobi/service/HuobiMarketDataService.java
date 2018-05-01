package com.qidiancamp.api.huobi.service;

import com.qidiancamp.Exchange;
import com.qidiancamp.currency.CurrencyPair;
import com.qidiancamp.dto.marketdata.Ticker;
import com.qidiancamp.api.huobi.HuobiAdapters;
import com.qidiancamp.service.marketdata.MarketDataService;

import java.io.IOException;

public class HuobiMarketDataService extends HuobiMarketDataServiceRaw implements MarketDataService {

  public HuobiMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    return HuobiAdapters.adaptTicker(getHuobiTicker(currencyPair), currencyPair);
  }

}
