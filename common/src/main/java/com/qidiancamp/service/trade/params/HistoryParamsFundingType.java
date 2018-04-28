package com.qidiancamp.service.trade.params;

import com.qidiancamp.dto.account.FundingRecord;

public interface HistoryParamsFundingType extends TradeHistoryParams {

  FundingRecord.Type getType();

  void setType(FundingRecord.Type type);
}
