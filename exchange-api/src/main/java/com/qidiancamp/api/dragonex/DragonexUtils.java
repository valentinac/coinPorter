package com.qidiancamp.api.dragonex;

import com.qidiancamp.exceptions.ExchangeException;

import java.util.Date;

public class DragonexUtils {
  public static Date nanos2Date(String nanos) {
    // timestamp is provided in nano seconds!
    try {
      long millis = Long.valueOf(nanos.substring(0, nanos.length() - 6));
      return new Date(millis);
    } catch (Throwable e) {
      throw new ExchangeException("Invalid timestamp provided by dragonex: " + nanos);
    }
  }
}
