package com.qidiancamp.service.trade.params;

public interface TradeHistoryParamsIdSpan extends TradeHistoryParams {

  String getStartId();

  void setStartId(String startId);

  String getEndId();

  void setEndId(String endId);
}
