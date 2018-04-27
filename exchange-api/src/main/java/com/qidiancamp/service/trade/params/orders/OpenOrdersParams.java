package com.qidiancamp.service.trade.params.orders;

import com.qidiancamp.dto.trade.LimitOrder;
import com.qidiancamp.service.trade.TradeService;

/**
 * Root interface for all interfaces used as a parameter type for {@link
 * TradeService#getOpenOrders(OpenOrdersParams)}. Each exchange should have its own class
 * implementing at least one from following available interfaces:
 *
 * <ul>
 *   <li>{@link OpenOrdersParamCurrencyPair}.
 * </ul>
 *
 * When suitable exchange params definition can extend from default classes, eg. {@link
 * DefaultOpenOrdersParamCurrencyPair}.
 */
public interface OpenOrdersParams {
  /**
   * Checks if passed order is suitable for open orders params. May be used for XChange side orders
   * filtering
   *
   * @return true if order is ok
   */
  boolean accept(LimitOrder order);
}
