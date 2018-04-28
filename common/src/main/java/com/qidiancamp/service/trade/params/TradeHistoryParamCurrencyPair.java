package com.qidiancamp.service.trade.params;

import com.qidiancamp.currency.CurrencyPair;

public interface TradeHistoryParamCurrencyPair extends TradeHistoryParams {

  CurrencyPair getCurrencyPair();

  void setCurrencyPair(CurrencyPair pair);
}
