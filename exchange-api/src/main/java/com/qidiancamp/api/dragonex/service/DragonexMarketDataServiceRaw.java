package com.qidiancamp.api.dragonex.service;


import com.qidiancamp.Exchange;
import com.qidiancamp.api.dragonex.dto.DragonResult;
import com.qidiancamp.api.dragonex.dto.DragonexException;
import com.qidiancamp.api.dragonex.dto.marketdata.Coin;
import com.qidiancamp.api.dragonex.dto.marketdata.Order;
import com.qidiancamp.api.dragonex.dto.marketdata.Symbol;
import com.qidiancamp.api.dragonex.dto.marketdata.Ticker;

import java.io.IOException;
import java.util.List;

public class DragonexMarketDataServiceRaw extends DragonexBaseService {

  public DragonexMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public List<Coin> coinAll() throws DragonexException, IOException {
    DragonResult<List<Coin>> coinAll = exchange.dragonexPublic().coinAll();
    return coinAll.getResult();
  }

  public List<Symbol> symbolAll() throws DragonexException, IOException {
    DragonResult<List<Symbol>> symbolAll = exchange.dragonexPublic().symbolAll();
    return symbolAll.getResult();
  }

  public List<Order> marketBuyOrders(long symbolId) throws DragonexException, IOException {
    DragonResult<List<Order>> marketBuyOrders = exchange.dragonexPublic().marketBuyOrders(symbolId);
    return marketBuyOrders.getResult();
  }

  public List<Order> marketSellOrders(long symbolId) throws DragonexException, IOException {
    DragonResult<List<Order>> marketSellOrders =
        exchange.dragonexPublic().marketSellOrders(symbolId);
    return marketSellOrders.getResult();
  }

  List<Ticker> ticker(long symbolId) throws DragonexException, IOException {
    DragonResult<List<Ticker>> ticker = exchange.dragonexPublic().ticker(symbolId);
    return ticker.getResult();
  }
}
