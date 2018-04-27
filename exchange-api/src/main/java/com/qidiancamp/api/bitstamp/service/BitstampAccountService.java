package com.qidiancamp.api.bitstamp.service;

import com.qidiancamp.Exchange;
import com.qidiancamp.api.bitstamp.BitstampAdapters;
import com.qidiancamp.api.bitstamp.BitstampUtils;
import com.qidiancamp.api.bitstamp.dto.account.BitstampDepositAddress;
import com.qidiancamp.api.bitstamp.dto.account.BitstampWithdrawal;
import com.qidiancamp.api.bitstamp.dto.trade.BitstampUserTransaction;
import com.qidiancamp.currency.Currency;
import com.qidiancamp.currency.CurrencyPair;
import com.qidiancamp.dto.account.AccountInfo;
import com.qidiancamp.dto.account.FundingRecord;
import com.qidiancamp.exceptions.ExchangeException;
import com.qidiancamp.exceptions.NotAvailableFromExchangeException;
import com.qidiancamp.exceptions.NotYetImplementedForExchangeException;
import com.qidiancamp.service.account.AccountService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/** @author Matija Mazi */
public class BitstampAccountService extends BitstampAccountServiceRaw implements AccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitstampAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return BitstampAdapters.adaptAccountInfo(
        getBitstampBalance(), exchange.getExchangeSpecification().getUserName());
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws IOException {
    return withdrawFunds(new DefaultWithdrawFundsParams(address, currency, amount));
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params)
      throws ExchangeException, NotAvailableFromExchangeException,
          NotYetImplementedForExchangeException, IOException {
    if (params instanceof RippleWithdrawFundsParams) {
      RippleWithdrawFundsParams rippleWithdrawFundsParams = (RippleWithdrawFundsParams) params;

      BitstampWithdrawal response =
          withdrawRippleFunds(
              rippleWithdrawFundsParams.amount,
              rippleWithdrawFundsParams.address,
              rippleWithdrawFundsParams.tag);

      if (response.error != null) {
        throw new ExchangeException("Failed to withdraw: " + response.error);
      }

      if (response.getId() == null) {
        return null;
      }

      return Integer.toString(response.getId());
    } else if (params instanceof DefaultWithdrawFundsParams) {
      DefaultWithdrawFundsParams defaultParams = (DefaultWithdrawFundsParams) params;

      BitstampWithdrawal response;
      if (defaultParams.currency.equals(Currency.LTC)) {
        response = withdrawLtcFunds(defaultParams.amount, defaultParams.address);
      } else if (defaultParams.currency.equals(Currency.ETH)) {
        response = withdrawEthFunds(defaultParams.amount, defaultParams.address);
      } else if (defaultParams.currency.equals(Currency.BTC)) {
        response = withdrawBtcFunds(defaultParams.amount, defaultParams.address);
      } else if (defaultParams.currency.equals(Currency.BCH)) {
        response = withdrawBchFunds(defaultParams.amount, defaultParams.address);
      } else {
        throw new IllegalStateException("Cannot withdraw " + defaultParams.currency);
      }

      if (response.error != null) {
        throw new ExchangeException("Failed to withdraw: " + response.error);
      }

      if (response.getId() == null) {
        return null;
      }

      return Integer.toString(response.getId());
    }

    throw new IllegalStateException("Don't know how to withdraw: " + params);
  }

  /**
   * This returns the currently set deposit address. It will not generate a new address (ie.
   * repeated calls will return the same address).
   */
  @Override
  public String requestDepositAddress(Currency currency, String... arguments) throws IOException {

    BitstampDepositAddress response = null;

    if (currency.equals(Currency.BTC)) {
      response = getBitstampBitcoinDepositAddress();
    } else if (currency.equals(Currency.LTC)) {
      response = getBitstampLitecoinDepositAddress();
    } else if (currency.equals(Currency.ETH)) {
      response = getBitstampEthereumDepositAddress();
    } else {
      throw new IllegalStateException("Unsupported currency " + currency);
    }

    return response.getDepositAddress();
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    return new BitstampTradeHistoryParams(null, BitstampUtils.MAX_TRANSACTIONS_PER_QUERY);
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params)
      throws ExchangeException, NotAvailableFromExchangeException,
          NotYetImplementedForExchangeException, IOException {
    Long limit = null;
    CurrencyPair currencyPair = null;
    Long offset = null;
    TradeHistoryParamsSorted.Order sort = null;
    if (params instanceof TradeHistoryParamPaging) {
      limit = Long.valueOf(((TradeHistoryParamPaging) params).getPageLength());
    }
    if (params instanceof TradeHistoryParamCurrencyPair) {
      currencyPair = ((TradeHistoryParamCurrencyPair) params).getCurrencyPair();
    }
    if (params instanceof TradeHistoryParamOffset) {
      offset = ((TradeHistoryParamOffset) params).getOffset();
    }
    if (params instanceof TradeHistoryParamsSorted) {
      sort = ((TradeHistoryParamsSorted) params).getOrder();
    }
    BitstampUserTransaction[] txs =
        getBitstampUserTransactions(
            limit, currencyPair, offset, sort == null ? null : sort.toString());
    return BitstampAdapters.adaptFundingHistory(Arrays.asList(txs));
  }
}
