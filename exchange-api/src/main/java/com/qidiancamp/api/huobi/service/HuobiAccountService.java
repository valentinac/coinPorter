package com.qidiancamp.api.huobi.service;

import com.qidiancamp.Exchange;
import com.qidiancamp.currency.Currency;
import com.qidiancamp.dto.account.AccountInfo;
import com.qidiancamp.dto.account.FundingRecord;
import com.qidiancamp.exceptions.ExchangeException;
import com.qidiancamp.api.huobi.HuobiAdapters;
import com.qidiancamp.api.huobi.dto.account.HuobiAccount;
import com.qidiancamp.service.account.AccountService;
import com.qidiancamp.service.trade.params.TradeHistoryParams;
import com.qidiancamp.service.trade.params.WithdrawFundsParams;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class HuobiAccountService extends HuobiAccountServiceRaw implements AccountService {

  public HuobiAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams withdrawFundsParams) throws IOException {
    return null;
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal bigDecimal, String s)
      throws IOException {
    return null;
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    HuobiAccount[] accounts = getAccounts();
    if (accounts.length == 0) {
      throw new ExchangeException("Account is not recognized.");
    }
    String accountID = String.valueOf(accounts[0].getId());
    return new AccountInfo(
        accountID,
        HuobiAdapters.adaptWallet(
            HuobiAdapters.adaptBalance(getHuobiBalance(accountID).getList())));
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    return null;
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams tradeHistoryParams)
      throws IOException {
    return null;
  }

  @Override
  public String requestDepositAddress(Currency currency, String... strings) throws IOException {
    return null;
  }
}
