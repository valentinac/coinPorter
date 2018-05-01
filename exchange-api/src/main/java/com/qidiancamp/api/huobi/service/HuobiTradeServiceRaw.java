package com.qidiancamp.api.huobi.service;

import com.qidiancamp.Exchange;
import com.qidiancamp.dto.Order.OrderType;
import com.qidiancamp.dto.trade.LimitOrder;
import com.qidiancamp.dto.trade.MarketOrder;
import com.qidiancamp.exceptions.ExchangeException;
import com.qidiancamp.api.huobi.HuobiUtils;
import com.qidiancamp.api.huobi.dto.trade.HuobiCreateOrderRequest;
import com.qidiancamp.api.huobi.dto.trade.HuobiOrder;
import com.qidiancamp.api.huobi.dto.trade.results.HuobiCancelOrderResult;
import com.qidiancamp.api.huobi.dto.trade.results.HuobiOrderInfoResult;
import com.qidiancamp.api.huobi.dto.trade.results.HuobiOrderResult;
import com.qidiancamp.api.huobi.dto.trade.results.HuobiOrdersResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class HuobiTradeServiceRaw extends HuobiBaseService {

  HuobiTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  HuobiOrder[] getHuobiOpenOrders() throws IOException {
    String states = "pre-submitted,submitted,partial-filled,partial-canceled,filled,canceled";
    HuobiOrdersResult result =
        huobi.getOpenOrders(
            states,
            exchange.getExchangeSpecification().getApiKey(),
            HuobiDigest.HMAC_SHA_256,
            2,
            HuobiUtils.createUTCDate(exchange.getNonceFactory()),
            signatureCreator);
    return checkResult(result);
  }

  String cancelHuobiOrder(String orderId) throws IOException {
    HuobiCancelOrderResult result =
        huobi.cancelOrder(
            orderId,
            exchange.getExchangeSpecification().getApiKey(),
            HuobiDigest.HMAC_SHA_256,
            2,
            HuobiUtils.createUTCDate(exchange.getNonceFactory()),
            signatureCreator);
    return checkResult(result);
  }

  String placeHuobiLimitOrder(LimitOrder limitOrder) throws IOException {
    String type;
    if (limitOrder.getType() == OrderType.BID) {
      type = "buy-limit";
    } else if (limitOrder.getType() == OrderType.ASK) {
      type = "sell-limit";
    } else {
      throw new ExchangeException("Unsupported order type.");
    }

    HuobiOrderResult result =
        huobi.placeLimitOrder(
            new HuobiCreateOrderRequest(
                limitOrder.getId(),
                limitOrder.getOriginalAmount().toString(),
                limitOrder.getLimitPrice().toString(),
                HuobiUtils.createHuobiCurrencyPair(limitOrder.getCurrencyPair()),
                type),
            exchange.getExchangeSpecification().getApiKey(),
            HuobiDigest.HMAC_SHA_256,
            2,
            HuobiUtils.createUTCDate(exchange.getNonceFactory()),
            signatureCreator);

    return checkResult(result);
  }

  String placeHuobiMarketOrder(MarketOrder limitOrder) throws IOException {
    String type;
    if (limitOrder.getType() == OrderType.BID) {
      type = "buy-market";
    } else if (limitOrder.getType() == OrderType.ASK) {
      type = "sell-market";
    } else {
      throw new ExchangeException("Unsupported order type.");
    }
    HuobiOrderResult result =
        huobi.placeMarketOrder(
            new HuobiCreateOrderRequest(
                limitOrder.getId(),
                limitOrder.getOriginalAmount().toString(),
                null,
                HuobiUtils.createHuobiCurrencyPair(limitOrder.getCurrencyPair()),
                type),
            exchange.getExchangeSpecification().getApiKey(),
            HuobiDigest.HMAC_SHA_256,
            2,
            HuobiUtils.createUTCDate(exchange.getNonceFactory()),
            signatureCreator);
    return checkResult(result);
  }

  List<HuobiOrder> getHuobiOrder(String... orderIds) throws IOException {
    List<HuobiOrder> orders = new ArrayList<>();
    for (String orderId : orderIds) {
      HuobiOrderInfoResult orderInfoResult =
          huobi.getOrder(
              orderId,
              exchange.getExchangeSpecification().getApiKey(),
              HuobiDigest.HMAC_SHA_256,
              2,
              HuobiUtils.createUTCDate(exchange.getNonceFactory()),
              signatureCreator);
      orders.add(checkResult(orderInfoResult));
    }
    return orders;
  }
}
