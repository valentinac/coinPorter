package com.qidiancamp.service.trade.params;

import com.qidiancamp.currency.Currency;
import java.math.BigDecimal;
import javax.annotation.Nullable;

public class RippleWithdrawFundsParams extends DefaultWithdrawFundsParams {
  @Nullable public final String tag; // optional

  public RippleWithdrawFundsParams(String address, Currency currency, BigDecimal amount) {
    this(address, currency, amount, null);
  }

  public RippleWithdrawFundsParams(
      String address, Currency currency, BigDecimal amount, String tag) {
    super(address, currency, amount);
    this.tag = tag;
  }

  @Override
  public String toString() {
    return "RippleWithdrawFundsParams{"
        + "address='"
        + getAddress()
        + '\''
        + ", tag='"
        + getTag()
        + '\''
        + ", currency="
        + getCurrency()
        + ", amount="
        + getAmount()
        + ", commission="
        + getCommission()
        + '}';
  }

  @Nullable
  public String getTag() {
    return tag;
  }
}
