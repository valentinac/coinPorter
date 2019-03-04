package com.qidiancamp.api.binance;

import com.qidiancamp.BaseExchange;
import com.qidiancamp.ExchangeSpecification;
import com.qidiancamp.api.binance.dto.marketdata.BinanceSymbolPrice;
import com.qidiancamp.api.binance.service.BinanceAccountService;
import com.qidiancamp.api.binance.service.BinanceMarketDataService;
import com.qidiancamp.api.binance.service.BinanceTradeService;
import com.qidiancamp.currency.Currency;
import com.qidiancamp.currency.CurrencyPair;
import com.qidiancamp.dto.meta.CurrencyMetaData;
import com.qidiancamp.dto.meta.CurrencyPairMetaData;
import com.qidiancamp.common.utils.jackson.CurrencyPairDeserializer;
import com.qidiancamp.common.utils.nonce.AtomicLongCurrentTimeIncrementalNonceFactory;
import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import si.mazi.rescu.SynchronizedValueFactory;

public class BinanceExchange extends BaseExchange {

  private SynchronizedValueFactory<Long> nonceFactory =
      new AtomicLongCurrentTimeIncrementalNonceFactory();

//  @Override
//  protected void initServices() {
//    this.marketDataService = new BinanceMarketDataService(this);
//    this.tradeService = new BinanceTradeService(this);
//    this.accountService = new BinanceAccountService(this);
//  }

  @Autowired
  public void setMarketDataServices(BinanceMarketDataService binanceMarketDataService){
    super.marketDataService = binanceMarketDataService;
  }
  @Autowired
  public void setTradeService(BinanceTradeService binanceTradeService){
    super.tradeService = binanceTradeService;
  }

  @Autowired
  public void setAccountService(BinanceAccountService binanceAccountService){
    super.accountService = binanceAccountService;
  }
  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification spec = new ExchangeSpecification(this.getClass().getCanonicalName());
    spec.setSslUri("https://www.binance.com");
    spec.setHost("www.binance.com");
    spec.setPort(80);
    spec.setExchangeName("Binance");
    spec.setExchangeDescription("Binance Exchange.");
    return spec;
  }

  @Override
  public void remoteInit() {
    try {
      // populate currency pair keys only, exchange does not provide any other metadata for download
      Map<CurrencyPair, CurrencyPairMetaData> currencyPairs = exchangeMetaData.getCurrencyPairs();
      Map<Currency, CurrencyMetaData> currencies = exchangeMetaData.getCurrencies();

      BinanceMarketDataService marketDataService =
          (BinanceMarketDataService) this.marketDataService;
      for (BinanceSymbolPrice price : marketDataService.tickerAllPrices()) {
        CurrencyPair pair = CurrencyPairDeserializer.getCurrencyPairFromString(price.symbol);
        currencyPairs.put(
            pair, new CurrencyPairMetaData(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, 8));

        currencies.put(pair.base, new CurrencyMetaData(8, BigDecimal.ZERO));
        currencies.put(pair.counter, new CurrencyMetaData(8, BigDecimal.ZERO));
      }
    } catch (Exception e) {
      logger.warn("An exception occurred while loading the metadata", e);
    }
  }
}
