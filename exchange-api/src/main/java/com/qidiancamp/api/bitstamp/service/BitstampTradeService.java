package com.qidiancamp.api.bitstamp.service;

import static com.qidiancamp.dto.Order.OrderType.BID;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.qidiancamp.Exchange;
import com.qidiancamp.api.bitstamp.BitstampAdapters;
import com.qidiancamp.api.bitstamp.BitstampAuthenticatedV2;
import com.qidiancamp.api.bitstamp.BitstampUtils;
import com.qidiancamp.api.bitstamp.dto.BitstampException;
import com.qidiancamp.api.bitstamp.dto.trade.BitstampOrder;
import com.qidiancamp.api.bitstamp.dto.trade.BitstampUserTransaction;
import com.qidiancamp.currency.CurrencyPair;
import com.qidiancamp.dto.Order;
import com.qidiancamp.dto.Order.OrderType;
import com.qidiancamp.dto.trade.LimitOrder;
import com.qidiancamp.dto.trade.MarketOrder;
import com.qidiancamp.dto.trade.OpenOrders;
import com.qidiancamp.dto.trade.UserTrades;
import com.qidiancamp.exceptions.ExchangeException;
import com.qidiancamp.service.trade.TradeService;
import com.qidiancamp.service.trade.params.CancelOrderByIdParams;
import com.qidiancamp.service.trade.params.CancelOrderParams;
import com.qidiancamp.service.trade.params.TradeHistoryParamCurrencyPair;
import com.qidiancamp.service.trade.params.TradeHistoryParamOffset;
import com.qidiancamp.service.trade.params.TradeHistoryParamPaging;
import com.qidiancamp.service.trade.params.TradeHistoryParams;
import com.qidiancamp.service.trade.params.TradeHistoryParamsSorted;
import com.qidiancamp.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import com.qidiancamp.service.trade.params.orders.OpenOrdersParams;
import org.springframework.stereotype.Service;

/** @author Matija Mazi */
public class BitstampTradeService extends BitstampTradeServiceRaw implements TradeService {

  public BitstampTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException, BitstampException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws ExchangeException, IOException {
    Collection<CurrencyPair> pairs = DefaultOpenOrdersParamCurrencyPair.getPairs(params, exchange);
    List<LimitOrder> limitOrders = new ArrayList<>();
    for (CurrencyPair pair : pairs) {
      BitstampOrder[] openOrders = getBitstampOpenOrders(pair);
      for (BitstampOrder bitstampOrder : openOrders) {
        OrderType orderType = bitstampOrder.getType() == 0 ? OrderType.BID : OrderType.ASK;
        String id = Integer.toString(bitstampOrder.getId());
        BigDecimal price = bitstampOrder.getPrice();
        limitOrders.add(
            new LimitOrder(
                orderType,
                bitstampOrder.getAmount(),
                pair,
                id,
                bitstampOrder.getDatetime(),
                price));
      }
    }
    return new OpenOrders(limitOrders);
  }

  @Override
  public String placeMarketOrder(MarketOrder order) throws IOException, BitstampException {
    BitstampAuthenticatedV2.Side side =
        order.getType().equals(BID)
            ? BitstampAuthenticatedV2.Side.buy
            : BitstampAuthenticatedV2.Side.sell;
    BitstampOrder bitstampOrder =
        placeBitstampMarketOrder(order.getCurrencyPair(), side, order.getOriginalAmount());
    if (bitstampOrder.getErrorMessage() != null) {
      throw new ExchangeException(bitstampOrder.getErrorMessage());
    }
    return Integer.toString(bitstampOrder.getId());
  }

  @Override
  public String placeLimitOrder(LimitOrder order) throws IOException, BitstampException {

    BitstampAuthenticatedV2.Side side =
        order.getType().equals(BID)
            ? BitstampAuthenticatedV2.Side.buy
            : BitstampAuthenticatedV2.Side.sell;
    BitstampOrder bitstampOrder =
        placeBitstampOrder(
            order.getCurrencyPair(), side, order.getOriginalAmount(), order.getLimitPrice());
    if (bitstampOrder.getErrorMessage() != null) {
      throw new ExchangeException(bitstampOrder.getErrorMessage());
    }
    return Integer.toString(bitstampOrder.getId());
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException, BitstampException {

    return cancelBitstampOrder(Integer.parseInt(orderId));
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    if (orderParams instanceof CancelOrderByIdParams) {
      return cancelOrder(((CancelOrderByIdParams) orderParams).getOrderId());
    } else {
      return false;
    }
  }

  /** Required parameter types: {@link TradeHistoryParamPaging#getPageLength()} */
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
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
    return BitstampAdapters.adaptTradeHistory(txs);
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {

    return new BitstampTradeHistoryParams(null, BitstampUtils.MAX_TRANSACTIONS_PER_QUERY);
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return new DefaultOpenOrdersParamCurrencyPair();
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) throws IOException {

    Collection<Order> orders = new ArrayList<>(orderIds.length);

    for (String orderId : orderIds) {
      orders.add(
          BitstampAdapters.adaptOrder(orderId, super.getBitstampOrder(Long.parseLong(orderId))));
    }

    return orders;
  }
}
