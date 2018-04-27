package com.qidiancamp.service.trade.params;

import com.qidiancamp.currency.Currency;

public interface TradeHistoryParamCurrency extends TradeHistoryParams {

  Currency getCurrency();

  void setCurrency(Currency currency);
}
