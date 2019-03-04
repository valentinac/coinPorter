package com.qidiancamp.api.bitstamp.service;

import java.io.IOException;
import java.math.BigDecimal;
import com.qidiancamp.Exchange;
import com.qidiancamp.api.bitstamp.BitstampAuthenticated;
import com.qidiancamp.api.bitstamp.BitstampAuthenticatedV2;
import com.qidiancamp.api.bitstamp.BitstampV2;
import com.qidiancamp.api.bitstamp.dto.BitstampException;
import com.qidiancamp.api.bitstamp.dto.trade.BitstampOrder;
import com.qidiancamp.api.bitstamp.dto.trade.BitstampOrderStatusResponse;
import com.qidiancamp.api.bitstamp.dto.trade.BitstampUserTransaction;
import com.qidiancamp.currency.CurrencyPair;
import org.springframework.beans.factory.annotation.Value;
import si.mazi.rescu.RestProxyFactory;
import si.mazi.rescu.SynchronizedValueFactory;

/** @author gnandiga */
public class BitstampTradeServiceRaw extends BitstampBaseService {

  private final BitstampAuthenticated bitstampAuthenticated;
  private final BitstampAuthenticatedV2 bitstampAuthenticatedV2;
  private final BitstampDigest signatureCreator;
  @Value("${exCoinfig.bitstamp.apikey}")
  private String apiKey;
  @Value("${exCoinfig.bitstamp.secret}")
  private String secret;
  @Value("${exCoinfig.bitstamp.userid}")
  private String userid;
  @Value("${exCoinfig.bitstamp.ssluri}")
  private String ssluri;
  private SynchronizedValueFactory<Long> nonceFactory;

  public BitstampTradeServiceRaw() {

//    super(exchange);
    this.bitstampAuthenticated =
        RestProxyFactory.createProxy(
            BitstampAuthenticated.class,ssluri,getClientConfig());
    this.bitstampAuthenticatedV2 =
        RestProxyFactory.createProxy(
            BitstampAuthenticatedV2.class,ssluri,getClientConfig());
//    this.apiKey = apiKey;
    this.nonceFactory = exchange.getNonceFactory();
    this.signatureCreator =
        BitstampDigest.createInstance(secret,userid,apiKey);
  }

  public BitstampOrder[] getBitstampOpenOrders(CurrencyPair pair) throws IOException {
    try {
      return bitstampAuthenticatedV2.getOpenOrders(
          apiKey, signatureCreator, nonceFactory, new BitstampV2.Pair(pair));
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public BitstampOrder placeBitstampMarketOrder(
      CurrencyPair pair, BitstampAuthenticatedV2.Side side, BigDecimal originalAmount)
      throws IOException {

    try {
      return bitstampAuthenticatedV2.placeMarketOrder(
          apiKey, signatureCreator, nonceFactory, side, new BitstampV2.Pair(pair), originalAmount);
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public BitstampOrder placeBitstampOrder(
      CurrencyPair pair,
      BitstampAuthenticatedV2.Side side,
      BigDecimal originalAmount,
      BigDecimal price)
      throws IOException {

    try {
      return bitstampAuthenticatedV2.placeOrder(
          apiKey,
          signatureCreator,
          nonceFactory,
          side,
          new BitstampV2.Pair(pair),
          originalAmount,
          price);
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public boolean cancelBitstampOrder(int orderId) throws IOException {

    try {
      return bitstampAuthenticated.cancelOrder(apiKey, signatureCreator, nonceFactory, orderId);
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public boolean cancelAllBitstampOrders() throws IOException {

    try {
      return bitstampAuthenticated.cancelAllOrders(apiKey, signatureCreator, nonceFactory);
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public BitstampUserTransaction[] getBitstampUserTransactions(
      Long numberOfTransactions, CurrencyPair pair) throws IOException {

    try {
      return bitstampAuthenticatedV2.getUserTransactions(
          apiKey,
          signatureCreator,
          nonceFactory,
          new BitstampV2.Pair(pair),
          numberOfTransactions,
          null,
          null);
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public BitstampUserTransaction[] getBitstampUserTransactions(Long numberOfTransactions)
      throws IOException {
    return getBitstampUserTransactions(numberOfTransactions, null, null);
  }

  public BitstampUserTransaction[] getBitstampUserTransactions(
      Long numberOfTransactions, CurrencyPair pair, Long offset, String sort) throws IOException {

    try {
      return bitstampAuthenticatedV2.getUserTransactions(
          apiKey,
          signatureCreator,
          nonceFactory,
          new BitstampV2.Pair(pair),
          numberOfTransactions,
          offset,
          sort);
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public BitstampUserTransaction[] getBitstampUserTransactions(
      Long numberOfTransactions, Long offset, String sort) throws IOException {
    try {
      return bitstampAuthenticatedV2.getUserTransactions(
          apiKey, signatureCreator, nonceFactory, numberOfTransactions, offset, sort);
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public BitstampOrderStatusResponse getBitstampOrder(Long orderId) throws IOException {
    try {
      return bitstampAuthenticated.getOrderStatus(
          exchange.getExchangeSpecification().getApiKey(),
          signatureCreator,
          exchange.getNonceFactory(),
          orderId);
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }
}
