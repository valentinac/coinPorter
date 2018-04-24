package com.qidiancamp.service.trade.params;

import com.qidiancamp.currency.CurrencyPair;
import java.util.Collection;

public interface TradeHistoryParamMultiCurrencyPair extends TradeHistoryParams {

  Collection<CurrencyPair> getCurrencyPairs();

  void setCurrencyPairs(Collection<CurrencyPair> pairs);
}
