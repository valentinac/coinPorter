package com.qidiancamp.api.binance.service;


import com.qidiancamp.currency.CurrencyPair;
import com.qidiancamp.service.trade.params.CancelOrderByCurrencyPair;
import com.qidiancamp.service.trade.params.CancelOrderByIdParams;

public class BinanceCancelOrderParams implements CancelOrderByIdParams, CancelOrderByCurrencyPair {
  private final String orderId;
  private final CurrencyPair pair;

  public BinanceCancelOrderParams(CurrencyPair pair, String orderId) {
    this.pair = pair;
    this.orderId = orderId;
  }

  @Override
  public CurrencyPair getCurrencyPair() {
    return pair;
  }

  @Override
  public String getOrderId() {
    return orderId;
  }
}
