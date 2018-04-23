package com.qidiancamp.service.trade.params.orders;

import com.qidiancamp.currency.CurrencyPair;
import com.qidiancamp.dto.trade.LimitOrder;

public interface OpenOrdersParamCurrencyPair extends OpenOrdersParams {
  @Override
  default boolean accept(LimitOrder order) {
    return order != null
        && getCurrencyPair() != null
        && getCurrencyPair().equals(order.getCurrencyPair());
  }

  CurrencyPair getCurrencyPair();

  void setCurrencyPair(CurrencyPair pair);
}
