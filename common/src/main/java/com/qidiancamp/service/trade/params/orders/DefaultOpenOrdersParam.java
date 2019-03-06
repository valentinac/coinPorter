package com.qidiancamp.service.trade.params.orders;


import com.qidiancamp.dto.trade.LimitOrder;

public class DefaultOpenOrdersParam implements OpenOrdersParams {

  public DefaultOpenOrdersParam() {}

  @Override
  public boolean accept(LimitOrder order) {
    return order != null;
  }
}
