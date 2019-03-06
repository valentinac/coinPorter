package com.qidiancamp.api.binance.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.qidiancamp.dto.Order.IOrderFlags;

public enum TimeInForce implements IOrderFlags {
  GTC,
  FOK,
  IOC;

  @JsonCreator
  public static TimeInForce getTimeInForce(String s) {
    try {
      return TimeInForce.valueOf(s);
    } catch (Exception e) {
      throw new RuntimeException("Unknown ordtime in force " + s + ".");
    }
  }
}
