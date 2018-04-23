package com.qidiancamp.api.bitstamp.service;

import com.qidiancamp.Exchange;
import com.qidiancamp.service.BaseExchangeService;
import com.qidiancamp.service.BaseService;

/**
 * @author timmolter
 */
public class BitstampBaseService extends BaseExchangeService implements BaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitstampBaseService(Exchange exchange) {

    super(exchange);
  }
}
