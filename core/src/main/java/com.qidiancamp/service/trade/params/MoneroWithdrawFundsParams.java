package com.qidiancamp.service.trade.params;

import com.qidiancamp.currency.Currency;
import java.math.BigDecimal;
import javax.annotation.Nullable;

public class MoneroWithdrawFundsParams extends DefaultWithdrawFundsParams {
  @Nullable public final String paymentId; // optional

  public MoneroWithdrawFundsParams(String address, Currency currency, BigDecimal amount) {
    this(address, currency, amount, null);
  }

  public MoneroWithdrawFundsParams(
      String address, Currency currency, BigDecimal amount, String paymentId) {
    super(address, currency, amount);
    this.paymentId = paymentId;
  }

  @Override
  public String toString() {
    return "MoneroWithdrawFundsParams{"
        + "address='"
        + getAddress()
        + '\''
        + ", paymentId='"
        + getPaymentId()
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
  public String getPaymentId() {
    return paymentId;
  }
}
