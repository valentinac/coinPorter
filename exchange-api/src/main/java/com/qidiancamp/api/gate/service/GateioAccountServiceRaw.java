package com.qidiancamp.api.gate.service;


import com.qidiancamp.Exchange;
import com.qidiancamp.api.gate.dto.GateioBaseResponse;
import com.qidiancamp.api.gate.dto.account.GateioDepositAddress;
import com.qidiancamp.api.gate.dto.account.GateioDepositsWithdrawals;
import com.qidiancamp.api.gate.dto.account.GateioFunds;
import com.qidiancamp.currency.Currency;
import com.qidiancamp.exceptions.ExchangeException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

public class GateioAccountServiceRaw extends GateioBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public GateioAccountServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public GateioFunds getGateioAccountInfo() throws IOException {

    GateioFunds gateioFunds =
        bter.getFunds(exchange.getExchangeSpecification().getApiKey(), signatureCreator);
    return handleResponse(gateioFunds);
  }

  public GateioDepositAddress getGateioDepositAddress(Currency currency) throws IOException {
    GateioDepositAddress depositAddress =
        bter.getDepositAddress(
            exchange.getExchangeSpecification().getApiKey(),
            signatureCreator,
            currency.getCurrencyCode());
    return depositAddress;
  }

  public String withdraw(
      Currency currency, BigDecimal amount, String baseAddress, String addressTag)
      throws IOException {
    String withdrawAddress = baseAddress;
    if (addressTag != null && addressTag.length() > 0) {
      withdrawAddress = withdrawAddress + " " + addressTag;
    }
    return withdraw(currency.getCurrencyCode(), amount, withdrawAddress);
  }

  public GateioDepositsWithdrawals getDepositsWithdrawals(Date start, Date end) throws IOException {
    GateioDepositsWithdrawals gateioDepositsWithdrawals =
        bter.getDepositsWithdrawals(
            exchange.getExchangeSpecification().getApiKey(),
            signatureCreator,
            start == null ? null : start.getTime() / 1000,
            end == null ? null : end.getTime() / 1000);
    return handleResponse(gateioDepositsWithdrawals);
  }

  public String withdraw(String currency, BigDecimal amount, String address) throws IOException {
    GateioBaseResponse withdraw =
        bter.withdraw(
            exchange.getExchangeSpecification().getApiKey(),
            signatureCreator,
            currency,
            amount,
            address);
    if (!withdraw.isResult()) {
      throw new ExchangeException(withdraw.getMessage());
    }
    // unfortunatelly gate.io does not return any id for the withdrawal
    return null;
  }
}
