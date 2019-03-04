package com.qidiancamp.api.huobi;

import com.qidiancamp.BaseExchange;
import com.qidiancamp.Exchange;
import com.qidiancamp.ExchangeSpecification;
import com.qidiancamp.common.utils.nonce.CurrentTimeNonceFactory;
import com.qidiancamp.exceptions.ExchangeException;
import com.qidiancamp.api.huobi.dto.marketdata.HuobiAsset;
import com.qidiancamp.api.huobi.dto.marketdata.HuobiAssetPair;
import com.qidiancamp.api.huobi.service.HuobiAccountService;
import com.qidiancamp.api.huobi.service.HuobiMarketDataService;
import com.qidiancamp.api.huobi.service.HuobiMarketDataServiceRaw;
import com.qidiancamp.api.huobi.service.HuobiTradeService;
import org.springframework.beans.factory.annotation.Autowired;
import si.mazi.rescu.SynchronizedValueFactory;

import java.io.IOException;

public class HuobiExchange extends BaseExchange implements Exchange {

  private final SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Autowired
  public void setMarketDataServices(HuobiMarketDataService huobiMarketDataService){
    super.marketDataService = huobiMarketDataService;
  }

  @Autowired
  public void setTradeService(HuobiTradeService huobiTradeService){
    super.tradeService = huobiTradeService;
  }

  @Autowired
  public void setAccountService(HuobiAccountService huobiAccountService){
    super.accountService = huobiAccountService;
  }

//  @Override
//  protected void initServices() {
//    this.marketDataService = new HuobiMarketDataService(this);
//    this.tradeService = new HuobiTradeService(this);
//    this.accountService = new HuobiAccountService(this);
//  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.huobi.pro");
    exchangeSpecification.setHost("api.huobi.pro");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Huobi");
    exchangeSpecification.setExchangeDescription(
        "Huobi is a Chinese digital currency trading platform and exchange based in Beijing");
    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }

  @Override
  public void remoteInit() throws IOException, ExchangeException {
    HuobiAssetPair[] assetPairs =
        ((HuobiMarketDataServiceRaw) marketDataService).getHuobiAssetPairs();
    HuobiAsset[] assets = ((HuobiMarketDataServiceRaw) marketDataService).getHuobiAssets();
    exchangeMetaData = HuobiAdapters.adaptToExchangeMetaData(assetPairs, assets);
  }
}
