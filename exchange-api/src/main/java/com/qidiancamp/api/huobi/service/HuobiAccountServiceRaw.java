package com.qidiancamp.api.huobi.service;

import com.qidiancamp.Exchange;
import com.qidiancamp.api.huobi.HuobiUtils;
import com.qidiancamp.api.huobi.dto.account.HuobiAccount;
import com.qidiancamp.api.huobi.dto.account.HuobiBalance;
import com.qidiancamp.api.huobi.dto.account.results.HuobiAccountResult;
import com.qidiancamp.api.huobi.dto.account.results.HuobiBalanceResult;

import java.io.IOException;

public class HuobiAccountServiceRaw extends HuobiBaseService {

  HuobiAccountServiceRaw(Exchange exchange) {
    super(exchange);
  }

  HuobiBalance getHuobiBalance(String accountID) throws IOException {
    HuobiBalanceResult huobiBalanceResult =
        huobi.getBalance(
            accountID,
            exchange.getExchangeSpecification().getApiKey(),
            HuobiDigest.HMAC_SHA_256,
            2,
            HuobiUtils.createUTCDate(exchange.getNonceFactory()),
            signatureCreator);
    return checkResult(huobiBalanceResult);
  }

  public HuobiAccount[] getAccounts() throws IOException {
    HuobiAccountResult huobiAccountResult =
        huobi.getAccount(
            exchange.getExchangeSpecification().getApiKey(),
            HuobiDigest.HMAC_SHA_256,
            2,
            HuobiUtils.createUTCDate(exchange.getNonceFactory()),
            signatureCreator);
    return checkResult(huobiAccountResult);
  }
}
