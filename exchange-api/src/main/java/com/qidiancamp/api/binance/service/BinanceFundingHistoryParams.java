package com.qidiancamp.api.binance.service;

import com.qidiancamp.currency.Currency;
import com.qidiancamp.dto.account.FundingRecord;
import com.qidiancamp.service.trade.params.HistoryParamsFundingType;
import com.qidiancamp.service.trade.params.TradeHistoryParamCurrency;
import com.qidiancamp.service.trade.params.TradeHistoryParamsTimeSpan;

import java.util.Date;

public class BinanceFundingHistoryParams
    implements TradeHistoryParamCurrency, TradeHistoryParamsTimeSpan, HistoryParamsFundingType {

  private Currency currency;
  private FundingRecord.Type type;
  private Date startTime;
  private Date endTime;

  @Override
  public Currency getCurrency() {
    return currency;
  }

  @Override
  public void setCurrency(Currency currency) {
    this.currency = currency;
  }

  @Override
  public FundingRecord.Type getType() {
    return type;
  }

  @Override
  public void setType(FundingRecord.Type type) {
    this.type = type;
  }

  @Override
  public Date getStartTime() {
    return startTime;
  }

  @Override
  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  @Override
  public Date getEndTime() {
    return endTime;
  }

  @Override
  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }
}
