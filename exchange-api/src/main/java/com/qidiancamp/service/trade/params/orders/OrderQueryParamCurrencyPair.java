package com.qidiancamp.service.trade.params.orders;

import com.qidiancamp.currency.CurrencyPair;

public interface OrderQueryParamCurrencyPair extends OrderQueryParams {
  CurrencyPair getCurrencyPair();

  void setCurrencyPair(CurrencyPair currencyPair);
}
