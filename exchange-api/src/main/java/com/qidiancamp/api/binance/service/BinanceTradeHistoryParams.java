package com.qidiancamp.api.binance.service;


import com.qidiancamp.currency.CurrencyPair;
import com.qidiancamp.service.trade.params.TradeHistoryParamCurrencyPair;
import com.qidiancamp.service.trade.params.TradeHistoryParamLimit;
import com.qidiancamp.service.trade.params.TradeHistoryParamsIdSpan;

public class BinanceTradeHistoryParams
    implements TradeHistoryParamCurrencyPair, TradeHistoryParamLimit, TradeHistoryParamsIdSpan {

  /** mandatory */
  private CurrencyPair currencyPair;
  /** optional */
  private Integer limit;
  /** optional */
  private String startId;
  /** ignored */
  private String endId;

  public BinanceTradeHistoryParams(CurrencyPair currencyPair) {
    this.currencyPair = currencyPair;
  }

  public BinanceTradeHistoryParams() {}

  public CurrencyPair getCurrencyPair() {
    return currencyPair;
  }

  public void setCurrencyPair(CurrencyPair currencyPair) {
    this.currencyPair = currencyPair;
  }

  public Integer getLimit() {
    return limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }

  public String getStartId() {
    return startId;
  }

  public void setStartId(String startId) {
    this.startId = startId;
  }

  public String getEndId() {
    return endId;
  }

  public void setEndId(String endId) {
    this.endId = endId;
  }
}
