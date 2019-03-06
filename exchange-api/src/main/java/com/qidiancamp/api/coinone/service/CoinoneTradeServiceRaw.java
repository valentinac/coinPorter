package com.qidiancamp.api.coinone.service;


import com.qidiancamp.Exchange;
import com.qidiancamp.api.coinone.dto.CoinoneException;
import com.qidiancamp.api.coinone.dto.trade.*;
import com.qidiancamp.currency.CurrencyPair;
import com.qidiancamp.dto.Order;
import com.qidiancamp.dto.trade.LimitOrder;

import java.io.IOException;

public class CoinoneTradeServiceRaw extends CoinoneBaseService {

  public CoinoneTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public CoinoneTradeResponse placeLimitOrderRaw(LimitOrder limitOrder)
      throws CoinoneException, IOException {
    CoinoneTradeRequest request =
        new CoinoneTradeRequest(
            apiKey,
            exchange.getNonceFactory().createValue(),
            limitOrder.getLimitPrice(),
            limitOrder.getOriginalAmount(),
            limitOrder.getCurrencyPair().base);
    if (limitOrder.getType().equals(Order.OrderType.ASK)) {
      return coinone.limitSell(payloadCreator, signatureCreator, request);
    } else {
      return coinone.limitBuy(payloadCreator, signatureCreator, request);
    }
  }

  public CoinoneTradeResponse cancerOrder(CoinoneTradeCancelRequest orderParams)
      throws CoinoneException, IOException {
    if (orderParams.getAccessTocken() == null || orderParams.getNonce() == null) {
      orderParams.setAccessTocken(apiKey);
      orderParams.setNonce(exchange.getNonceFactory().createValue());
    }
    return coinone.cancelOrder(payloadCreator, signatureCreator, orderParams);
  }

  public CoinoneOrderInfoResponse getOrderInfo(String orderId, CurrencyPair currencyPair)
      throws CoinoneException, IOException {
    CoinoneOrderInfoRequest request =
        new CoinoneOrderInfoRequest(
            apiKey,
            exchange.getNonceFactory().createValue(),
            orderId,
            currencyPair.base.getSymbol().toLowerCase());
    return coinone.getOrder(payloadCreator, signatureCreator, request);
  }
}
