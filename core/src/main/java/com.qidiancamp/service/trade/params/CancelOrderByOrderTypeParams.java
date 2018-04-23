package com.qidiancamp.service.trade.params;

import com.qidiancamp.dto.Order.OrderType;

public interface CancelOrderByOrderTypeParams extends CancelOrderParams {
  OrderType getOrderType();
}
