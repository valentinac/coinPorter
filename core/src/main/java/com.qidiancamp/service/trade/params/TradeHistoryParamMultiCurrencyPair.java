package com.qidiancamp.service.trade.params;

import java.util.Collection;
import com.qidiancamp.currency.CurrencyPair;

public interface TradeHistoryParamMultiCurrencyPair extends TradeHistoryParams {

  Collection<CurrencyPair> getCurrencyPairs();

  void setCurrencyPairs(Collection<CurrencyPair> pairs);
}
