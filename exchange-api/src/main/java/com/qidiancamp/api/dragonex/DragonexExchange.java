package com.qidiancamp.api.dragonex;


import com.qidiancamp.BaseExchange;
import com.qidiancamp.Exchange;
import com.qidiancamp.ExchangeSpecification;
import com.qidiancamp.api.dragonex.dto.DragonexException;
import com.qidiancamp.api.dragonex.dto.Token;
import com.qidiancamp.api.dragonex.dto.marketdata.Coin;
import com.qidiancamp.api.dragonex.dto.marketdata.Symbol;
import com.qidiancamp.api.dragonex.service.*;
import com.qidiancamp.currency.CurrencyPair;
import com.qidiancamp.exceptions.ExchangeException;
import si.mazi.rescu.ClientConfig;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;
import si.mazi.rescu.SynchronizedValueFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class DragonexExchange extends BaseExchange implements Exchange {

  private Dragonex dragonexPublic;
  private DragonexAuthenticated dragonexAuthenticated;
  private ParamsDigest signatureCreator;
  private final AtomicReference<Token> currentToken = new AtomicReference<>();
  private final Map<Long, String> coins = new HashMap<>();
  private Map<Long, CurrencyPair> symbols = new HashMap<>();
  private Map<CurrencyPair, Long> pairs = new HashMap<>();

  @Override
  protected void initServices() {
    this.marketDataService = new DragonexMarketDataService(this);
    this.accountService = new DragonexAccountService(this);
    this.tradeService = new DragonexTradeService(this);

    ClientConfig rescuConfig =
        ((DragonexMarketDataService) this.marketDataService).getClientConfig();
    ExchangeSpecification spec = this.getExchangeSpecification();
    this.dragonexPublic =
        RestProxyFactory.createProxy(Dragonex.class, spec.getSslUri(), rescuConfig);

    if (spec.getApiKey() != null && spec.getSecretKey() != null) {
      this.dragonexAuthenticated =
          RestProxyFactory.createProxy(DragonexAuthenticated.class, spec.getSslUri(), rescuConfig);
      this.signatureCreator = new DragonDigest(spec.getApiKey(), spec.getSecretKey());
    }

    Token token = (Token) spec.getExchangeSpecificParametersItem("dragonex.token");
    currentToken.set(token);
  }

  public CurrencyPair pair(long symbolId) {
    return symbols.get(symbolId);
  }

  public long symbolId(CurrencyPair pair) {
    Long symbolId = pairs.get(pair);
    if (symbolId == null) {
      throw new ExchangeException("Not supported pair " + pair + " by Dragonex.");
    }
    return symbolId;
  }

  public Token getOrCreateToken() throws DragonexException, IOException {
    Token token = currentToken.get();
    if (token != null && token.valid()) {
      return token;
    }
    synchronized (currentToken) {
      token = currentToken.get();
      if (token != null && token.valid()) {
        return token;
      }
      token = ((DragonexAccountServiceRaw) accountService).tokenNew();
      currentToken.set(token);
    }
    return token;
  }

  public Dragonex dragonexPublic() {
    return dragonexPublic;
  }

  public DragonexAuthenticated dragonexAuthenticated() {
    return dragonexAuthenticated;
  }

  public ParamsDigest signatureCreator() {
    return signatureCreator;
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification spec = new ExchangeSpecification(this.getClass().getCanonicalName());
    spec.setSslUri("https://openapi.dragonex.io/");
    spec.setHost("openapi.dragonex.io");
    spec.setPort(80);
    spec.setExchangeName("Dragonex");
    spec.setExchangeDescription("Dragonex is a bitcoin and altcoin exchange.");
    return spec;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    throw new ExchangeException("Dragonex does not require a nonce factory.");
  }

  @Override
  public void remoteInit() throws IOException, ExchangeException {
    try {
      List<Coin> coinAll = ((DragonexMarketDataService) marketDataService).coinAll();
      coinAll.forEach(c -> coins.put(c.coinId, c.code));
      List<Symbol> symbolAll = ((DragonexMarketDataService) marketDataService).symbolAll();
      symbolAll.forEach(
          c -> {
            CurrencyPair pair = new CurrencyPair(c.symbol.toUpperCase().replace('_', '/'));
            symbols.put(c.symbolId, pair);
            pairs.put(pair, c.symbolId);
          });
    } catch (Throwable e) {
      throw new RuntimeException("Could not initialize the Dragonex service.", e);
    }
  }
}
