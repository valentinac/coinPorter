package com.qidiancamp.api.bitstamp.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/** @author Matija Mazi */
public final class BitstampTicker {

  private final BigDecimal open;
  private final BigDecimal last;
  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal vwap;
  private final BigDecimal volume;
  private final BigDecimal bid;
  private final BigDecimal ask;
  private final long timestamp;

  /**
   * Constructor
   *
   * @param open
   * @param last
   * @param high
   * @param low
   * @param vwap
   * @param volume
   * @param bid
   * @param ask
   */
  public BitstampTicker(
      @JsonProperty("open") BigDecimal open,
      @JsonProperty("last") BigDecimal last,
      @JsonProperty("high") BigDecimal high,
      @JsonProperty("low") BigDecimal low,
      @JsonProperty("vwap") BigDecimal vwap,
      @JsonProperty("volume") BigDecimal volume,
      @JsonProperty("bid") BigDecimal bid,
      @JsonProperty("ask") BigDecimal ask,
      @JsonProperty("timestamp") long timestamp) {

    this.open = open;
    this.last = last;
    this.high = high;
    this.low = low;
    this.vwap = vwap;
    this.volume = volume;
    this.bid = bid;
    this.ask = ask;
    this.timestamp = timestamp;
  }

  public BigDecimal getOpen() {
    return open;
  }

  public BigDecimal getLast() {

    return last;
  }

  public BigDecimal getHigh() {

    return high;
  }

  public BigDecimal getLow() {

    return low;
  }

  public BigDecimal getVwap() {

    return vwap;
  }

  public BigDecimal getVolume() {

    return volume;
  }

  public BigDecimal getBid() {

    return bid;
  }

  public BigDecimal getAsk() {

    return ask;
  }

  public long getTimestamp() {

    return timestamp;
  }

  @Override
  public String toString() {

    return "BitstampTicker [open="
        + open
        + ", last="
        + last
        + ", high="
        + high
        + ", low="
        + low
        + ", vwap="
        + vwap
        + ", volume="
        + volume
        + ", bid="
        + bid
        + ", ask="
        + ask
        + ", timestamp="
        + timestamp
        + "]";
  }
}
