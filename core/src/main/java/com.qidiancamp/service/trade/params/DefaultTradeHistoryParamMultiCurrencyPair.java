package com.qidiancamp.service.trade.params;

import java.util.Collection;
import java.util.Collections;
import com.qidiancamp.currency.CurrencyPair;

public class DefaultTradeHistoryParamMultiCurrencyPair
    implements TradeHistoryParamMultiCurrencyPair {

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
