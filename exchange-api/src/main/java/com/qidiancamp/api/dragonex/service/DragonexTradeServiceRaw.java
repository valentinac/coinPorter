package com.qidiancamp.api.dragonex.service;


import com.qidiancamp.Exchange;
import com.qidiancamp.api.dragonex.dto.DragonResult;
import com.qidiancamp.api.dragonex.dto.DragonexException;
import com.qidiancamp.api.dragonex.dto.trade.*;

import java.io.IOException;

public class DragonexTradeServiceRaw extends DragonexBaseService {

  public DragonexTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public UserOrder orderBuy(String token, OrderPlacement orderPlacement)
      throws DragonexException, IOException {
    DragonResult<UserOrder> orderBuy =
        exchange
            .dragonexAuthenticated()
            .orderBuy(utcNow(), token, exchange.signatureCreator(), ContentSHA1, orderPlacement);
    return orderBuy.getResult();
  }

  public UserOrder orderSell(String token, OrderPlacement orderPlacement)
      throws DragonexException, IOException {
    DragonResult<UserOrder> orderSell =
        exchange
            .dragonexAuthenticated()
            .orderSell(utcNow(), token, exchange.signatureCreator(), ContentSHA1, orderPlacement);
    return orderSell.getResult();
  }

  public UserOrder orderCancel(String token, OrderReference ref)
      throws DragonexException, IOException {
    DragonResult<UserOrder> order =
        exchange
            .dragonexAuthenticated()
            .orderCancel(utcNow(), token, exchange.signatureCreator(), ContentSHA1, ref);
    return order.getResult();
  }

  public UserOrder orderDetail(String token, OrderReference ref)
      throws DragonexException, IOException {
    DragonResult<UserOrder> order =
        exchange
            .dragonexAuthenticated()
            .orderDetail(utcNow(), token, exchange.signatureCreator(), ContentSHA1, ref);
    return order.getResult();
  }

  public OrderHistory orderHistory(String token, OrderHistoryRequest req)
      throws DragonexException, IOException {
    DragonResult<OrderHistory> orderHistory =
        exchange
            .dragonexAuthenticated()
            .orderHistory(utcNow(), token, exchange.signatureCreator(), ContentSHA1, req);
    return orderHistory.getResult();
  }

  public DealHistory dealHistory(String token, DealHistoryRequest req)
      throws DragonexException, IOException {
    DragonResult<DealHistory> dealHistory =
        exchange
            .dragonexAuthenticated()
            .dealHistory(utcNow(), token, exchange.signatureCreator(), ContentSHA1, req);
    return dealHistory.getResult();
  }
}
