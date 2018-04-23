package com.qidiancamp.service.trade.params;

import com.qidiancamp.currency.Currency;

public interface TradeHistoryParamCurrencies extends TradeHistoryParams {

  Currency[] getCurrencies();

  void setCurrencies(Currency[] currencies);
}
