package com.qidiancamp.api.coinone.service;

import com.qidiancamp.Exchange;
import com.qidiancamp.api.coinone.dto.CoinoneException;
import com.qidiancamp.api.coinone.dto.trade.CoinoneTradeCancelRequest;
import com.qidiancamp.api.coinone.dto.trade.CoinoneTradeResponse;
import com.qidiancamp.dto.Order;
import com.qidiancamp.dto.trade.LimitOrder;
import com.qidiancamp.dto.trade.OpenOrders;
import com.qidiancamp.dto.trade.StopOrder;
import com.qidiancamp.exceptions.ExchangeException;
import com.qidiancamp.exceptions.NotAvailableFromExchangeException;
import com.qidiancamp.exceptions.NotYetImplementedForExchangeException;
import com.qidiancamp.service.trade.TradeService;
import com.qidiancamp.service.trade.params.CancelOrderParams;
import com.qidiancamp.service.trade.params.orders.OrderQueryParams;

import java.io.IOException;
import java.util.Collection;

/** @author interwater */
public class CoinoneTradeService extends CoinoneTradeServiceRaw implements TradeService {
  /**
   * Constructor
   *
   * @param exchange
   */
  public CoinoneTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder)
      throws IOException, NotAvailableFromExchangeException {
    CoinoneTradeResponse response = super.placeLimitOrderRaw(limitOrder);
    if (!response.getErrorCode().equals("0")) {
      throw new CoinoneException(CoinoneException.resMsgMap.get(response.getErrorCode()));
    }
    return response.getOrderId();
  }

  @Override
  public String placeStopOrder(StopOrder stopOrder) throws IOException {
    return null;
  }

  @Override
  public Collection<Order> getOrder(OrderQueryParams... orderQueryParams) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams)
      throws ExchangeException, NotAvailableFromExchangeException,
          NotYetImplementedForExchangeException, IOException {
    CoinoneTradeResponse response = super.cancerOrder((CoinoneTradeCancelRequest) orderParams);
    if (!response.getErrorCode().equals("0")) {
      throw new CoinoneException(CoinoneException.resMsgMap.get(response.getErrorCode()));
    }
    return true;
  }
}
