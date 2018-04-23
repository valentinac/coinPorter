package com.qidiancamp.api.gate.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.qidiancamp.api.GateioBaseResponse;

import java.util.List;

/**
 * Data object representing depth from Bter
 */
public class GateioDepth extends GateioBaseResponse {

  private final List<GateioPublicOrder> asks;
  private final List<GateioPublicOrder> bids;

  /**
   * Constructor
   *
   * @param asks
   * @param bids
   */
  private GateioDepth(@JsonProperty("asks") List<GateioPublicOrder> asks, @JsonProperty("bids") List<GateioPublicOrder> bids,
      @JsonProperty("result") boolean result) {

    super(result, null);
    this.asks = asks;
    this.bids = bids;
  }

  public List<GateioPublicOrder> getAsks() {

    return asks;
  }

  public List<GateioPublicOrder> getBids() {

    return bids;
  }

  @Override
  public String toString() {

    return "GateioDepth [asks=" + asks.toString() + ", bids=" + bids.toString() + "]";
  }

}
