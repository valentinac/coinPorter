package com.qidiancamp.api.dragonex.dto;


import com.qidiancamp.dto.LoanOrder;

import java.util.List;

/** DTO representing loan information */
public final class LoanInfo {

  /** Provided loans */
  private final List<LoanOrder> providedLoans;

  /** Used loans */
  private final List<LoanOrder> usedLoans;

  /**
   * Constructs an {@link LoanInfo}.
   *
   * @param providedLoans provided loans.
   * @param usedLoans used loans.
   */
  public LoanInfo(List<LoanOrder> providedLoans, List<LoanOrder> usedLoans) {
    this.providedLoans = providedLoans;
    this.usedLoans = usedLoans;
  }

  public List<LoanOrder> getProvidedLoans() {
    return providedLoans;
  }

  public List<LoanOrder> getUsedLoans() {
    return usedLoans;
  }
}
