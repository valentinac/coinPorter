package com.qidiancamp.api.binance_hs.domain.account;

import com.qidiancamp.api.binance_hs.constant.BinanceApiConstants;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * History of account withdrawals.
 *
 * @see Withdraw
 */
public class WithdrawHistory {

  private List<Withdraw> withdrawList;

  private boolean success;

  public List<Withdraw> getWithdrawList() {
    return withdrawList;
  }

  public void setWithdrawList(List<Withdraw> withdrawList) {
    this.withdrawList = withdrawList;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, BinanceApiConstants.TO_STRING_BUILDER_STYLE)
        .append("withdrawList", withdrawList)
        .append("success", success)
        .toString();
  }
}
