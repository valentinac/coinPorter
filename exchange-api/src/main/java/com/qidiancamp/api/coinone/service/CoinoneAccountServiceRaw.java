package com.qidiancamp.api.coinone.service;


import com.qidiancamp.Exchange;
import com.qidiancamp.api.coinone.dto.CoinoneException;
import com.qidiancamp.api.coinone.dto.account.CoinoneBalancesRequest;
import com.qidiancamp.api.coinone.dto.account.CoinoneBalancesResponse;
import com.qidiancamp.api.coinone.dto.account.CoinoneWithdrawRequest;
import com.qidiancamp.api.coinone.dto.account.CoinoneWithdrawResponse;
import com.qidiancamp.currency.Currency;

import java.io.IOException;
import java.math.BigDecimal;

public class CoinoneAccountServiceRaw extends CoinoneBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CoinoneAccountServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public CoinoneBalancesResponse getWallet() throws CoinoneException, IOException {
    CoinoneBalancesRequest request =
        new CoinoneBalancesRequest(apiKey, exchange.getNonceFactory().createValue());
    CoinoneBalancesResponse response = coinone.getWallet(payloadCreator, signatureCreator, request);
    return response;
  }

  public CoinoneWithdrawResponse withdrawFund(
          Currency currency, BigDecimal amount, String address, String authNumber)
      throws CoinoneException, IOException {
    CoinoneWithdrawRequest request =
        new CoinoneWithdrawRequest(
            apiKey,
            exchange.getNonceFactory().createValue(),
            currency.getSymbol().toLowerCase(),
            authNumber,
            amount.doubleValue(),
            address);
    CoinoneWithdrawResponse response =
        coinone.withdrawFund(payloadCreator, signatureCreator, request);
    return response;
  }
}
