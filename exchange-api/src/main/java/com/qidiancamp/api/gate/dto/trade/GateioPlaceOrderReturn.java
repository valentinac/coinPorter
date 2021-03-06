package com.qidiancamp.api.gate.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.qidiancamp.api.gate.dto.GateioBaseResponse;

public class GateioPlaceOrderReturn extends GateioBaseResponse {

  private final String orderNumber;

  /** Constructor */
  private GateioPlaceOrderReturn(
      @JsonProperty("result") boolean result,
      @JsonProperty("orderNumber") String orderNumber,
      @JsonProperty("msg") String message) {

    super(result, message);
    this.orderNumber = orderNumber;
  }

  public String getOrderId() {

    return orderNumber;
  }

  @Override
  public String toString() {

    return "GateioPlaceOrderReturn [orderNumber=" + orderNumber + "]";
  }
}
