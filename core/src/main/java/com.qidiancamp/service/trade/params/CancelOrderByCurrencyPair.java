package com.qidiancamp.service.trade.params;

import com.qidiancamp.currency.CurrencyPair;

public interface CancelOrderByCurrencyPair extends CancelOrderParams {

  public CurrencyPair getCurrencyPair();
}
