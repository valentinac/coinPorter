package com.qidiancamp.api.binance.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public final class BinanceBalance {

  public final String asset;
  public final BigDecimal free;
  public final BigDecimal locked;

  public BinanceBalance(
      @JsonProperty("asset") String asset,
      @JsonProperty("free") BigDecimal free,
      @JsonProperty("locked") BigDecimal locked) {
    this.asset = asset;
    this.free = free;
    this.locked = locked;
  }
}
