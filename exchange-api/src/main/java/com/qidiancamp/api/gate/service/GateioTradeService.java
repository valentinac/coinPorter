package com.qidiancamp.api.gate.service;

import com.qidiancamp.Exchange;
import com.qidiancamp.api.gate.GateioAdapters;
import com.qidiancamp.api.gate.dto.trade.GateioOpenOrders;
import com.qidiancamp.api.gate.dto.trade.GateioTrade;
import com.qidiancamp.currency.CurrencyPair;
import com.qidiancamp.dto.trade.LimitOrder;
import com.qidiancamp.dto.trade.MarketOrder;
import com.qidiancamp.dto.trade.OpenOrders;
import com.qidiancamp.dto.trade.UserTrades;
import com.qidiancamp.exceptions.ExchangeException;
import com.qidiancamp.exceptions.NotAvailableFromExchangeException;
import com.qidiancamp.service.trade.TradeService;
import com.qidiancamp.service.trade.params.CancelOrderParams;
import com.qidiancamp.service.trade.params.DefaultTradeHistoryParamCurrencyPair;
import com.qidiancamp.service.trade.params.TradeHistoryParamCurrencyPair;
import com.qidiancamp.service.trade.params.TradeHistoryParams;
import com.qidiancamp.service.trade.params.orders.OpenOrdersParams;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class GateioTradeService extends GateioTradeServiceRaw implements TradeService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public GateioTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    GateioOpenOrders openOrders = super.getGateioOpenOrders();
    Collection<CurrencyPair> currencyPairs = exchange.getExchangeSymbols();

    return GateioAdapters.adaptOpenOrders(openOrders, currencyPairs);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  /**
   * Submits a Limit Order to be executed on the Gateio Exchange for the desired market defined by
   * {@code CurrencyPair}. WARNING - Gateio will return true regardless of whether or not an order
   * actually gets created. The reason for this is that orders are simply submitted to a queue in
   * their back-end. One example for why an order might not get created is because there are
   * insufficient funds. The best attempt you can make to confirm that the order was created is to
   * poll {@link #getOpenOrders}. However, if the order is created and executed before it is caught
   * in its open state from calling {@link #getOpenOrders} then the only way to confirm would be
   * confirm the expected difference in funds available for your account.
   *
   * @return String "true"/"false" Used to determine if the order request was submitted
   *     successfully.
   */
  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    return String.valueOf(super.placeGateioLimitOrder(limitOrder));
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    if (orderParams instanceof GateioCancelOrderParams) {
      return cancelOrder(
          ((GateioCancelOrderParams) orderParams).getOrderId(),
          ((GateioCancelOrderParams) orderParams).getCurrencyPair());
    } else {
      return false;
    }
  }

  /** Required parameter: {@link TradeHistoryParamCurrencyPair} */
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params)
      throws ExchangeException, IOException {

    CurrencyPair pair = ((TradeHistoryParamCurrencyPair) params).getCurrencyPair();
    List<GateioTrade> userTrades = getGateioTradeHistory(pair).getTrades();

    return GateioAdapters.adaptUserTrades(userTrades);
  }

  @Override
  public TradeHistoryParamCurrencyPair createTradeHistoryParams() {

    return new DefaultTradeHistoryParamCurrencyPair();
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return null;
  }
}
