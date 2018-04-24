package com.qidiancamp.api.binance.service;

import com.qidiancamp.Exchange;
import com.qidiancamp.api.binance.BinanceAuthenticated;
import com.qidiancamp.service.BaseExchangeService;
import com.qidiancamp.service.BaseService;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

public class BinanceBaseService extends BaseExchangeService implements BaseService {

  protected final String apiKey;
  protected final BinanceAuthenticated binance;
  protected final ParamsDigest signatureCreator;

  /**
   * Constructor
   *
   * @param exchange
   */
  protected BinanceBaseService(Exchange exchange) {
    super(exchange);
    this.binance =
        RestProxyFactory.createProxy(
            BinanceAuthenticated.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator =
        BinanceHmacDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }
}
