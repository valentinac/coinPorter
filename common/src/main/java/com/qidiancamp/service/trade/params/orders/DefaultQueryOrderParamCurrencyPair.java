package com.qidiancamp.service.trade.params.orders;


import com.qidiancamp.currency.CurrencyPair;

public class DefaultQueryOrderParamCurrencyPair extends DefaultQueryOrderParam
    implements OrderQueryParamCurrencyPair {

  private CurrencyPair pair;

  public DefaultQueryOrderParamCurrencyPair() {
    super();
  }

  public DefaultQueryOrderParamCurrencyPair(CurrencyPair pair, String orderId) {
    super(orderId);
    this.pair = pair;
  }

  @Override
  public CurrencyPair getCurrencyPair() {
    return pair;
  }

  @Override
  public void setCurrencyPair(CurrencyPair currencyPair) {
    pair = currencyPair;
  }
}
