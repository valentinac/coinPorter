package com.qidiancamp.api.gate.service;

import com.qidiancamp.Exchange;
import com.qidiancamp.api.GateioBaseResponse;
import com.qidiancamp.api.gate.GateioUtils;
import com.qidiancamp.api.gate.dto.GateioOrderType;
import com.qidiancamp.api.gate.dto.trade.GateioOpenOrders;
import com.qidiancamp.api.gate.dto.trade.GateioOrderStatus;
import com.qidiancamp.api.gate.dto.trade.GateioPlaceOrderReturn;
import com.qidiancamp.api.gate.dto.trade.GateioTradeHistoryReturn;
import com.qidiancamp.currency.CurrencyPair;
import com.qidiancamp.dto.Order;
import com.qidiancamp.dto.trade.LimitOrder;
import java.io.IOException;
import java.math.BigDecimal;

public class GateioTradeServiceRaw extends GateioBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public GateioTradeServiceRaw(Exchange exchange) {

    super(exchange);
  }

  /**
   * Submits a Limit Order to be executed on the Gateio Exchange for the desired market defined by
   * {@code CurrencyPair}. WARNING - Gateio will return true regardless of whether or not an order
   * actually gets created. The reason for this is that orders are simply submitted to a queue in
   * their back-end. One example for why an order might not get created is because there are
   * insufficient funds. The best attempt you can make to confirm that the order was created is to
   * poll {@link #getGateioOpenOrders}. However if the order is created and executed before it is
   * caught in its open state from calling {@link #getGateioOpenOrders} then the only way to confirm
   * would be confirm the expected difference in funds available for your account.
   *
   * @param limitOrder
   * @return String order id of submitted request.
   * @throws java.io.IOException
   */
  public String placeGateioLimitOrder(LimitOrder limitOrder) throws IOException {

    GateioOrderType type =
        (limitOrder.getType() == Order.OrderType.BID) ? GateioOrderType.BUY : GateioOrderType.SELL;

    return placeGateioLimitOrder(
        limitOrder.getCurrencyPair(),
        type,
        limitOrder.getLimitPrice(),
        limitOrder.getOriginalAmount());
  }

  /**
   * Submits a Limit Order to be executed on the Gateio Exchange for the desired market defined by
   * {@code currencyPair}. WARNING - Gateio will return true regardless of whether or not an order
   * actually gets created. The reason for this is that orders are simply submitted to a queue in
   * their back-end. One example for why an order might not get created is because there are
   * insufficient funds. The best attempt you can make to confirm that the order was created is to
   * poll {@link #getGateioOpenOrders}. However if the order is created and executed before it is
   * caught in its open state from calling {@link #getGateioOpenOrders} then the only way to confirm
   * would be confirm the expected difference in funds available for your account.
   *
   * @param currencyPair
   * @param orderType
   * @param rate
   * @param amount
   * @return String order id of submitted request.
   * @throws java.io.IOException
   */
  public String placeGateioLimitOrder(
      CurrencyPair currencyPair, GateioOrderType orderType, BigDecimal rate, BigDecimal amount)
      throws IOException {

    String pair = formatCurrencyPair(currencyPair);

    GateioPlaceOrderReturn orderId;
    if (orderType.equals(GateioOrderType.BUY)) {
      orderId = bter.buy(pair, rate, amount, apiKey, signatureCreator, exchange.getNonceFactory());
    } else {
      orderId = bter.sell(pair, rate, amount, apiKey, signatureCreator, exchange.getNonceFactory());
    }

    return handleResponse(orderId).getOrderId();
  }

  public boolean cancelOrder(String orderId) throws IOException {

    GateioBaseResponse cancelOrderResult =
        bter.cancelOrder(orderId, apiKey, signatureCreator, exchange.getNonceFactory());

    return handleResponse(cancelOrderResult).isResult();
  }

  /**
   * Cancels all orders. See https://gate.io/api2.
   *
   * @param type order type(0:sell,1:buy,-1:all)
   * @param currencyPair currency pair
   * @return
   * @throws java.io.IOException
   */
  public boolean cancelAllOrders(String type, CurrencyPair currencyPair) throws IOException {

    GateioBaseResponse cancelAllOrdersResult =
        bter.cancelAllOrders(
            type,
            formatCurrencyPair(currencyPair),
            apiKey,
            signatureCreator,
            exchange.getNonceFactory());

    return handleResponse(cancelAllOrdersResult).isResult();
  }

  public GateioOpenOrders getGateioOpenOrders() throws IOException {

    GateioOpenOrders gateioOpenOrdersReturn =
        bter.getOpenOrders(apiKey, signatureCreator, exchange.getNonceFactory());

    return handleResponse(gateioOpenOrdersReturn);
  }

  public GateioOrderStatus getGateioOrderStatus(String orderId) throws IOException {

    GateioOrderStatus orderStatus =
        bter.getOrderStatus(orderId, apiKey, signatureCreator, exchange.getNonceFactory());

    return handleResponse(orderStatus);
  }

  public GateioTradeHistoryReturn getGateioTradeHistory(CurrencyPair currencyPair)
      throws IOException {

    GateioTradeHistoryReturn gateioTradeHistoryReturn =
        bter.getUserTradeHistory(
            apiKey,
            signatureCreator,
            exchange.getNonceFactory(),
            GateioUtils.toPairString(currencyPair));

    return handleResponse(gateioTradeHistoryReturn);
  }

  private String formatCurrencyPair(CurrencyPair currencyPair) {
    return String.format(
            "%s_%s", currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode())
        .toLowerCase();
  }
}
