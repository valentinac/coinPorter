package com.qidiancamp.api.gate.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.qidiancamp.api.gate.dto.GateioBaseResponse;

import java.util.List;

public class GateioDepositsWithdrawals extends GateioBaseResponse {

  private final List<GateioDeposit> deposits;
  private final List<GateioWithdrawal> withdraws;

  public GateioDepositsWithdrawals(
      @JsonProperty("result") boolean result,
      @JsonProperty("deposits") List<GateioDeposit> deposits,
      @JsonProperty("withdraws") List<GateioWithdrawal> withdraws,
      @JsonProperty("message") final String message) {
    super(result, message);
    this.deposits = deposits;
    this.withdraws = withdraws;
  }

  public List<GateioDeposit> getDeposits() {
    return deposits;
  }

  public List<GateioWithdrawal> getWithdraws() {
    return withdraws;
  }

  @Override
  public String toString() {
    return "GateioDepositsWithdrawals [deposits=" + deposits + ", withdraws=" + withdraws + "]";
  }
}
