package com.qidiancamp.api.bitstamp.order.dto;

import java.math.BigDecimal;
import java.util.Date;
import com.qidiancamp.currency.CurrencyPair;
import com.qidiancamp.dto.Order;

public class BitstampGenericOrder extends Order {

  public BitstampGenericOrder(
      OrderType type,
      BigDecimal originalAmount,
      CurrencyPair currencyPair,
      String id,
      Date timestamp,
      BigDecimal averagePrice,
      BigDecimal cumulativeAmount,
      BigDecimal fee,
      OrderStatus status) {

    super(
        type,
        originalAmount,
        currencyPair,
        id,
        timestamp,
        averagePrice,
        cumulativeAmount,
        fee,
        status);
  }
}
