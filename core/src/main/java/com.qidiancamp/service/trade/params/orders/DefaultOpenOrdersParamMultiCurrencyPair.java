package com.qidiancamp.service.trade.params.orders;

import java.util.Collection;
import java.util.Collections;
import com.qidiancamp.currency.CurrencyPair;

public class DefaultOpenOrdersParamMultiCurrencyPair implements OpenOrdersParamMultiCurrencyPair {

  private Collection<CurrencyPair> pairs = Collections.emptySet();

  @Override
  public Collection<CurrencyPair> getCurrencyPairs() {
    return pairs;
  }

  @Override
  public void setCurrencyPairs(Collection<CurrencyPair> value) {
    pairs = value;
  }
}
