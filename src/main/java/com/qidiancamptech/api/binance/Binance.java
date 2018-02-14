package com.qidiancamptech.api.binance;

import org.knowm.xchange.binance.dto.BinanceException;
import org.knowm.xchange.binance.dto.marketdata.*;
import org.knowm.xchange.binance.dto.meta.BinanceTime;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public interface Binance {

  @GET
  @Path("api/v1/ping")
  /**
   * Test connectivity to the Rest API.
   * @return
   * @throws java.io.IOException
   */
  Object ping() throws IOException;

  @GET
  @Path("api/v1/time")
  /**
   * Test connectivity to the Rest API and get the current server time.
   * @return
   * @throws java.io.IOException
   */
  BinanceTime time() throws IOException;

  @GET
  @Path("api/v1/depth")
  /**
   *
   * @param symbol
   * @param limit optional, default 100; max 100.
   * @return
   * @throws java.io.IOException
   * @throws BinanceException
   */
  BinanceOrderbook depth(@QueryParam("symbol") String symbol, @QueryParam("limit") Integer limit)
      throws IOException, BinanceException;

  @GET
  @Path("api/v1/aggTrades")
  /**
   * Get compressed, aggregate trades. Trades that fill at the time, from the same order, with the same price will
   * have the quantity aggregated.<br/>
   * If both startTime and endTime are sent, limit should not be sent AND the distance between startTime and endTime
   * must be less than 24 hours.<br/>
   * If frondId, startTime, and endTime are not sent, the most recent aggregate trades will be returned.
   * @param symbol
   * @param fromId    optional, ID to get aggregate trades from INCLUSIVE.
   * @param startTime optional, Timestamp in ms to get aggregate trades from INCLUSIVE.
   * @param endTime   optional, Timestamp in ms to get aggregate trades until INCLUSIVE.
   * @param limit     optional, Default 500; max 500.
   * @return
   * @throws java.io.IOException
   * @throws BinanceException
   */
  List<BinanceAggTrades> aggTrades(@QueryParam("symbol") String symbol, @QueryParam("fromId") Long fromId
          , @QueryParam("startTime") Long startTime, @QueryParam("endTime") Long endTime
          , @QueryParam("limit") Integer limit)
      throws IOException, BinanceException;

  @GET
  @Path("api/v1/klines")
  /**
   * Kline/candlestick bars for a symbol. Klines are uniquely identified by their open time.<br/>
   * If startTime and endTime are not sent, the most recent klines are returned.
   * @param symbol
   * @param interval
   * @param limit     optional, default 500; max 500.
   * @param startTime optional
   * @param endTime   optional
   * @return
   * @throws java.io.IOException
   * @throws BinanceException
   */
  List<Object[]> klines(@QueryParam("symbol") String symbol, @QueryParam("interval") KlineInterval interval
          , @QueryParam("limit") Integer limit, @QueryParam("startTime") Long startTime
          , @QueryParam("endTime") Long endTime)
      throws IOException, BinanceException;

  @GET
  @Path("api/v1/ticker/24hr")
  /**
   * 24 hour price change statistics.
   * @param symbol
   * @return
   * @throws java.io.IOException
   * @throws BinanceException
   */
  BinanceTicker24h ticker24h(@QueryParam("symbol") String symbol) throws IOException, BinanceException;

  @GET
  @Path("api/v1/ticker/allPrices")
  /**
   * Latest price for all symbols.
   * @return
   * @throws java.io.IOException
   * @throws BinanceException
   */
  List<BinanceSymbolPrice> tickerAllPrices() throws IOException, BinanceException;

  @GET
  @Path("api/v1/ticker/allBookTickers")
  /**
   * Best price/qty on the order book for all symbols.
   * @return
   * @throws java.io.IOException
   * @throws BinanceException
   */
  List<BinancePriceQuantity> tickerAllBookTickers() throws IOException, BinanceException;

}
