package com.qidiancamp.service.trade.params.orders;

import com.qidiancamp.currency.CurrencyPair;
import com.qidiancamp.dto.trade.LimitOrder;
import java.util.Collection;

public interface OpenOrdersParamMultiCurrencyPair extends OpenOrdersParams {
  @Override
  default boolean accept(LimitOrder order) {
    return order != null
        && getCurrencyPairs() != null
        && getCurrencyPairs().contains(order.getCurrencyPair());
  }

  Collection<CurrencyPair> getCurrencyPairs();

  void setCurrencyPairs(Collection<CurrencyPair> pairs);
}
