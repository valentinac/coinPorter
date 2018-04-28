package com.qidiancamp.service.trade.params;

import com.qidiancamp.service.trade.TradeService;
import java.util.Date;

/**
 * Parameters type for {@link TradeService#getTradeHistory(TradeHistoryParams)} with start and end
 * timestamps.
 */
public interface TradeHistoryParamsTimeSpan extends TradeHistoryParams {

  Date getStartTime();

  void setStartTime(Date startTime);

  Date getEndTime();

  void setEndTime(Date endTime);
}
