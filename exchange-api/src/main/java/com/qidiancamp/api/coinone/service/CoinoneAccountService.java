package com.qidiancamp.api.coinone.service;



import com.qidiancamp.Exchange;
import com.qidiancamp.api.coinone.CoinoneAdapters;
import com.qidiancamp.api.coinone.CoinoneExchange;
import com.qidiancamp.currency.Currency;
import com.qidiancamp.dto.account.AccountInfo;
import com.qidiancamp.exceptions.ExchangeException;
import com.qidiancamp.exceptions.NotAvailableFromExchangeException;
import com.qidiancamp.exceptions.NotYetImplementedForExchangeException;
import com.qidiancamp.service.account.AccountService;
import com.qidiancamp.service.trade.params.WithdrawFundsParams;

import java.io.IOException;
import java.math.BigDecimal;

public class CoinoneAccountService extends CoinoneAccountServiceRaw implements AccountService {

  private CoinoneExchange coinoneExchange;

  /**
   * Constructor
   *
   * @param exchange
   */
  public CoinoneAccountService(Exchange exchange) {
    super(exchange);
    this.coinoneExchange = (CoinoneExchange) exchange;
  }

  @Override
  public AccountInfo getAccountInfo()
      throws ExchangeException, NotAvailableFromExchangeException,
          NotYetImplementedForExchangeException, IOException {
    return new AccountInfo(CoinoneAdapters.adaptWallet(super.getWallet()));
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws IOException {
    return null;
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams withdrawFundsParams) throws IOException {
    return null;
  }
}
