package com.qidiancamp.api.huobi.service;

import com.qidiancamp.Exchange;
import com.qidiancamp.currency.CurrencyPair;
import com.qidiancamp.api.huobi.HuobiUtils;
import com.qidiancamp.api.huobi.dto.marketdata.HuobiAssetPair;
import com.qidiancamp.api.huobi.dto.marketdata.HuobiTicker;
import com.qidiancamp.api.huobi.dto.marketdata.results.HuobiAssetPairsResult;
import com.qidiancamp.api.huobi.dto.marketdata.results.HuobiTickerResult;

import java.io.IOException;

public class HuobiMarketDataServiceRaw extends HuobiBaseService {

  public HuobiMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public HuobiTicker getHuobiTicker(CurrencyPair currencyPair) throws IOException {
    String huobiCurrencyPair = HuobiUtils.createHuobiCurrencyPair(currencyPair);
    HuobiTickerResult tickerResult = huobi.getTicker(huobiCurrencyPair);
    return checkResult(tickerResult);
  }

  public HuobiAssetPair[] getHuobiAssetPairs() throws IOException {
    HuobiAssetPairsResult assetPairsResult = huobi.getAssetPairs();
    return checkResult(assetPairsResult);
  }
}
