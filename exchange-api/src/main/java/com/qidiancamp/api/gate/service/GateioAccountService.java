package com.qidiancamp.api.gate.service;

import com.qidiancamp.Exchange;
import com.qidiancamp.api.gate.GateioAdapters;
import com.qidiancamp.currency.Currency;
import com.qidiancamp.dto.account.AccountInfo;
import com.qidiancamp.dto.account.FundingRecord;
import com.qidiancamp.exceptions.NotAvailableFromExchangeException;
import com.qidiancamp.exceptions.NotYetImplementedForExchangeException;
import com.qidiancamp.service.account.AccountService;
import com.qidiancamp.service.trade.params.TradeHistoryParams;
import com.qidiancamp.service.trade.params.WithdrawFundsParams;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class GateioAccountService extends GateioAccountServiceRaw implements AccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public GateioAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return new AccountInfo(GateioAdapters.adaptWallet(super.getGateioAccountInfo()));
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }
}
