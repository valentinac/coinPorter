package com.qidiancamp.api.bitstamp;

import com.qidiancamp.BaseExchange;
import com.qidiancamp.Exchange;
import com.qidiancamp.ExchangeSpecification;
import com.qidiancamp.api.bitstamp.service.BitstampAccountService;
import com.qidiancamp.api.bitstamp.service.BitstampMarketDataService;
import com.qidiancamp.api.bitstamp.service.BitstampTradeService;
import com.qidiancamp.common.utils.nonce.CurrentTimeNonceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import si.mazi.rescu.SynchronizedValueFactory;

/** @author Matija Mazi */
@Service
public class BitstampExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Value("${exCoinfig.bitstamp.ssluri}")
  private String ssluri;
  @Value("${exCoinfig.bitstamp.host}")
  private String host;
  @Value("${exCoinfig.bitstamp.port}")
  private Integer port;
  @Value("${exCoinfig.bitstamp.name}")
  private String exchangeName;
  @Value("${exCoinfig.bitstamp.discription}")
  private String discription;
//  @Autowired
//  public void setMarketDataServices(BitstampMarketDataService bitstampMarketDataService){
//    super.marketDataService = bitstampMarketDataService;
//  }
//
//  @Autowired
//  public void setTradeService(BitstampTradeService bitstampTradeService){
//    super.tradeService = bitstampTradeService;
//  }
//
//  @Autowired
//  public void setAccountService(BitstampAccountService bitstampAccountService){
//    super.accountService = bitstampAccountService;
//  }

  /**
   * 使用spring的方式管理service
   */
  @Override
  protected void initServices() {
    this.marketDataService = new BitstampMarketDataService(this);
    this.tradeService = new BitstampTradeService(this);
    this.accountService = new BitstampAccountService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri(ssluri);
    exchangeSpecification.setHost(host);
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName(exchangeName);
    exchangeSpecification.setExchangeDescription(discription);
    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }
}
