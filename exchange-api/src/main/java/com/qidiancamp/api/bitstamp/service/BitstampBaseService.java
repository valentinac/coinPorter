package com.qidiancamp.api.bitstamp.service;

import com.qidiancamp.Exchange;
import com.qidiancamp.api.bitstamp.dto.BitstampException;
import com.qidiancamp.exceptions.ExchangeException;
import com.qidiancamp.exceptions.FundsExceededException;
import com.qidiancamp.exceptions.InternalServerException;
import com.qidiancamp.exceptions.NonceException;
import com.qidiancamp.exceptions.RateLimitExceededException;
import com.qidiancamp.service.BaseExchangeService;
import com.qidiancamp.service.BaseService;

/** @author timmolter */
public class BitstampBaseService extends BaseExchangeService implements BaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitstampBaseService(Exchange exchange) {

    super(exchange);
  }

  protected ExchangeException handleError(BitstampException exception) {

    if (exception.getMessage().contains("You can only buy")) {
      return new FundsExceededException(exception);

    } else if (exception.getMessage().contains("Invalid limit exceeded")) {
      return new RateLimitExceededException(exception);

    } else if (exception.getMessage().contains("Invalid nonce")) {
      return new NonceException(exception.getMessage());

    } else if (exception.getMessage().contains("Internal server error")) {
      return new InternalServerException(exception);

    } else {
      return new ExchangeException(exception);
    }
  }
}
