package com.qidiancamp.api.gate.service;

import com.qidiancamp.Exchange;
import com.qidiancamp.api.gate.GateioAuthenticated;
import com.qidiancamp.api.gate.dto.GateioBaseResponse;
import com.qidiancamp.exceptions.ExchangeException;
import com.qidiancamp.service.BaseExchangeService;
import com.qidiancamp.service.BaseService;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

public class GateioBaseService extends BaseExchangeService implements BaseService {

  protected final String apiKey;
  protected final GateioAuthenticated bter;
  protected final ParamsDigest signatureCreator;

  /**
   * Constructor
   *
   * @param exchange
   */
  public GateioBaseService(Exchange exchange) {

    super(exchange);

    this.bter =
        RestProxyFactory.createProxy(
            GateioAuthenticated.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator =
        GateioHmacPostBodyDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

  protected <R extends GateioBaseResponse> R handleResponse(R response) {

    if (!response.isResult()) {
      throw new ExchangeException(response.getMessage());
    }

    return response;
  }
}
