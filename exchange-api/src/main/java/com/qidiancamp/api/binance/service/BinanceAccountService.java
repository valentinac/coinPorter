package com.qidiancamp.api.binance.service;

import com.qidiancamp.Exchange;
import com.qidiancamp.api.binance.BinanceErrorAdapter;
import com.qidiancamp.api.binance.dto.BinanceException;
import com.qidiancamp.api.binance.dto.account.BinanceAccountInformation;
import com.qidiancamp.currency.Currency;
import com.qidiancamp.dto.account.AccountInfo;
import com.qidiancamp.dto.account.Balance;
import com.qidiancamp.dto.account.FundingRecord;
import com.qidiancamp.dto.account.Wallet;
import com.qidiancamp.service.account.AccountService;
import com.qidiancamp.service.trade.params.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class BinanceAccountService extends BinanceAccountServiceRaw implements AccountService {

  public BinanceAccountService(Exchange exchange) {
    super(exchange);
  }

  /** (0:Email Sent,1:Cancelled 2:Awaiting Approval 3:Rejected 4:Processing 5:Failure 6Completed) */
  private static FundingRecord.Status withdrawStatus(int status) {
    switch (status) {
      case 0:
      case 2:
      case 4:
        return FundingRecord.Status.PROCESSING;
      case 1:
        return FundingRecord.Status.CANCELLED;
      case 3:
      case 5:
        return FundingRecord.Status.FAILED;
      case 6:
        return FundingRecord.Status.COMPLETE;
      default:
        throw new RuntimeException("Unknown binance withdraw status: " + status);
    }
  }

  /** (0:pending,1:success) */
  private static FundingRecord.Status depositStatus(int status) {
    switch (status) {
      case 0:
        return FundingRecord.Status.PROCESSING;
      case 1:
        return FundingRecord.Status.COMPLETE;
      default:
        throw new RuntimeException("Unknown binance deposit status: " + status);
    }
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    try {
      Long recvWindow =
          (Long)
              exchange.getExchangeSpecification().getExchangeSpecificParametersItem("recvWindow");
      BinanceAccountInformation acc = super.account(recvWindow, getTimestamp());
      List<Balance> balances =
          acc.balances.stream()
              .map(b -> new Balance(b.getCurrency(), b.getTotal(), b.getAvailable()))
              .collect(Collectors.toList());
      return new AccountInfo(new Wallet(balances));
    } catch (BinanceException e) {
      throw BinanceErrorAdapter.adapt(e);
    }
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws IOException {
    try {
      return super.withdraw(currency.getCurrencyCode(), address, amount);
    } catch (BinanceException e) {
      throw BinanceErrorAdapter.adapt(e);
    }
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {
    try {
      if (!(params instanceof DefaultWithdrawFundsParams)) {
        throw new IllegalArgumentException("DefaultWithdrawFundsParams must be provided.");
      }
      String id = null;
      if (params instanceof RippleWithdrawFundsParams) {
        RippleWithdrawFundsParams rippleParams = null;
        rippleParams = (RippleWithdrawFundsParams) params;
        id =
            super.withdraw(
                rippleParams.getCurrency().getCurrencyCode(),
                rippleParams.getAddress(),
                rippleParams.getTag(),
                rippleParams.getAmount());
      } else {
        DefaultWithdrawFundsParams p = (DefaultWithdrawFundsParams) params;
        id = super.withdraw(p.getCurrency().getCurrencyCode(), p.getAddress(), p.getAmount());
      }
      return id;
    } catch (BinanceException e) {
      throw BinanceErrorAdapter.adapt(e);
    }
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {
    try {
      return super.requestDepositAddress(currency).address;
    } catch (BinanceException e) {
      throw BinanceErrorAdapter.adapt(e);
    }
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    return new BinanceFundingHistoryParams();
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
    try {
      String asset = null;
      if (params instanceof TradeHistoryParamCurrency) {
        TradeHistoryParamCurrency cp = (TradeHistoryParamCurrency) params;
        if (cp.getCurrency() != null) {
          asset = cp.getCurrency().getCurrencyCode();
        }
      }
      Long recvWindow =(Long)exchange.getExchangeSpecification().getExchangeSpecificParametersItem("recvWindow");

      boolean withdrawals = true;
      boolean deposits = true;

      Long startTime = null;
      Long endTime = null;
      if (params instanceof TradeHistoryParamsTimeSpan) {
        TradeHistoryParamsTimeSpan tp = (TradeHistoryParamsTimeSpan) params;
        if (tp.getStartTime() != null) {
          startTime = tp.getStartTime().getTime();
        }
        if (tp.getEndTime() != null) {
          endTime = tp.getEndTime().getTime();
        }
      }

      if (params instanceof HistoryParamsFundingType) {
        HistoryParamsFundingType f = (HistoryParamsFundingType) params;
        if (f.getType() != null) {
          withdrawals = f.getType() == FundingRecord.Type.WITHDRAWAL;
          deposits = f.getType() == FundingRecord.Type.DEPOSIT;
        }
      }

      List<FundingRecord> result = new ArrayList<>();
      if (withdrawals) {
        super.withdrawHistory(asset, startTime, endTime, recvWindow, getTimestamp())
            .forEach(
                w -> {
                  result.add(
                      new FundingRecord(
                          w.address,
                          new Date(w.applyTime),
                          Currency.getInstance(w.asset),
                          w.amount,
                          w.id,
                          w.txId,
                          FundingRecord.Type.WITHDRAWAL,
                          withdrawStatus(w.status),
                          null,
                          null,
                          null));
                });
      }

      if (deposits) {
        super.depositHistory(asset, startTime, endTime, recvWindow, getTimestamp())
            .forEach(
                d -> {
                  result.add(
                      new FundingRecord(
                          d.address,
                          new Date(d.insertTime),
                          Currency.getInstance(d.asset),
                          d.amount,
                          null,
                          d.txId,
                          FundingRecord.Type.DEPOSIT,
                          depositStatus(d.status),
                          null,
                          null,
                          null));
                });
      }

      return result;
    } catch (BinanceException e) {
      throw BinanceErrorAdapter.adapt(e);
    }
  }
}
