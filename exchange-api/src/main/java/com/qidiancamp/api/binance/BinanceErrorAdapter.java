package com.qidiancamp.api.binance;

import com.qidiancamp.api.binance.dto.BinanceException;
import com.qidiancamp.exceptions.CurrencyPairNotValidException;
import com.qidiancamp.exceptions.ExchangeException;
import org.apache.commons.lang3.StringUtils;

/** @author walec51 */
public final class BinanceErrorAdapter {

  private BinanceErrorAdapter() {}

  public static ExchangeException adapt(BinanceException e) {
    String message = e.getMessage();
    if (StringUtils.isEmpty(message)) {
      message = "Operation failed without any error message";
    }
    switch (e.getCode()) {
      case -1121:
        return new CurrencyPairNotValidException(message, e);
    }
    return new ExchangeException(message, e);
  }
}
