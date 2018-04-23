package com.qidiancamp.service.trade.params;

import com.qidiancamp.currency.CurrencyPair;

public class DefaultTradeHistoryParamCurrencyPair implements TradeHistoryParamCurrencyPair {

  private CurrencyPair pair;

  public DefaultTradeHistoryParamCurrencyPair() {}

  public DefaultTradeHistoryParamCurrencyPair(CurrencyPair pair) {
    this.pair = pair;
  }

  @Override
  public CurrencyPair getCurrencyPair() {

    return pair;
  }

  @Override
  public void setCurrencyPair(CurrencyPair pair) {

    this.pair = pair;
  }
}
