package com.qidiancamp.api.dragonex.dto.trade;


import com.qidiancamp.currency.CurrencyPair;
import com.qidiancamp.service.trade.params.CancelOrderByCurrencyPair;
import com.qidiancamp.service.trade.params.CancelOrderByIdParams;

public class DragonexCancelOrderParams implements CancelOrderByCurrencyPair, CancelOrderByIdParams {

  private CurrencyPair currencyPair;
  private String orderId;

  @Override
  public CurrencyPair getCurrencyPair() {
    return currencyPair;
  }

  @Override
  public String getOrderId() {
    return orderId;
  }

  public void setCurrencyPair(CurrencyPair currencyPair) {
    this.currencyPair = currencyPair;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }
}
