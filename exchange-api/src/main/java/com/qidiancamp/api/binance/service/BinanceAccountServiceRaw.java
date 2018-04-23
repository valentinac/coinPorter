package com.qidiancamp.api.binance.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qidiancamp.Exchange;
import com.qidiancamp.api.binance.dto.BinanceException;
import com.qidiancamp.api.binance.dto.account.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class BinanceAccountServiceRaw extends BinanceBaseService {

  public BinanceAccountServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public BinanceAccountInformation account(Long recvWindow, long timestamp) throws BinanceException, IOException {
    return binance.account(recvWindow, timestamp, super.apiKey, super.signatureCreator);
  }

  // the /wapi endpoint of binance is not stable yet and can be changed in future, there is also a lack of current documentation

  public String withdraw(String asset, String address, BigDecimal amount, String name, Long recvWindow
      , long timestamp) throws IOException, BinanceException {
    WithdrawRequest result = binance.withdraw(asset, address, null, amount, name, recvWindow, timestamp, super.apiKey, super.signatureCreator);
    checkWapiResponse(result);
    return result.getData();
  }

  public String withdraw(String asset, String address, String addressTag, BigDecimal amount, String name, Long recvWindow
      , long timestamp) throws IOException, BinanceException {
    WithdrawRequest result = binance.withdraw(asset, address, addressTag, amount, name, recvWindow, timestamp, super.apiKey, super.signatureCreator);
    checkWapiResponse(result);
    return result.getData();
  }

  public List<DepositList.BinanceDeposit> depositHistory(String asset, Long startTime, Long endTime, Long recvWindow, long timestamp) throws BinanceException, IOException {
    DepositList result = binance.depositHistory(asset, startTime, endTime, recvWindow, timestamp, super.apiKey, super.signatureCreator);
    return checkWapiResponse(result);
  }

  public List<WithdrawList.BinanceWithdraw> withdrawHistory(String asset, Long startTime, Long endTime, Long recvWindow, long timestamp) throws BinanceException, IOException {
    WithdrawList result = binance.withdrawHistory(asset, startTime, endTime, recvWindow, timestamp, super.apiKey, super.signatureCreator);
    return checkWapiResponse(result);
  }

  private <T> T checkWapiResponse(WapiResponse<T> result) throws IOException {
    if (!result.success) {
      BinanceException exception;
      try {
        exception = new ObjectMapper().readValue(result.msg, BinanceException.class);
      } catch (Throwable e) {
        exception = new BinanceException(-1, result.msg);
      }
      throw exception;
    }
    return result.getData();
  }
}