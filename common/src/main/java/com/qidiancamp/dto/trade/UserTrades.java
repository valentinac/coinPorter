package com.qidiancamp.dto.trade;


import com.qidiancamp.dto.marketdata.Trades;

import java.util.List;

public class UserTrades extends Trades {

  public UserTrades(List<UserTrade> trades, TradeSortType tradeSortType) {

    super((List) trades, tradeSortType);
  }

  public UserTrades(List<UserTrade> trades, long lastID, TradeSortType tradeSortType) {

    super((List) trades, lastID, tradeSortType);
  }

  public UserTrades(
      List<UserTrade> trades, long lastID, TradeSortType tradeSortType, String nextPageCursor) {
    super((List) trades, lastID, tradeSortType, nextPageCursor);
  }

  public List<UserTrade> getUserTrades() {

    return (List) getTrades();
  }
}
