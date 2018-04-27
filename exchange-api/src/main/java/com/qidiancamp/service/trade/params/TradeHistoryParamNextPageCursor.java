package com.qidiancamp.service.trade.params;

public interface TradeHistoryParamNextPageCursor extends TradeHistoryParams {
  String getNextPageCursor();

  void setNextPageCursor(String cursor);
}
