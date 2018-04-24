package com.qidiancamp.api.bitstamp;

import com.qidiancamp.api.bitstamp.dto.BitstampException;
import com.qidiancamp.api.bitstamp.dto.marketdata.BitstampOrderBook;
import com.qidiancamp.api.bitstamp.dto.marketdata.BitstampTicker;
import com.qidiancamp.api.bitstamp.dto.marketdata.BitstampTransaction;
import com.qidiancamp.api.bitstamp.service.BitstampMarketDataServiceRaw;
import com.qidiancamp.currency.CurrencyPair;
import com.qidiancamp.utils.jackson.CurrencyPairDeserializer;
import java.io.IOException;
import java.util.Objects;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/** @author Matija Mazi See https://www.bitstamp.net/api/ for up-to-date docs. */
@Path("api/v2")
@Produces(MediaType.APPLICATION_JSON)
public interface BitstampV2 {

  @GET
  @Path("order_book/{pair}/")
  BitstampOrderBook getOrderBook(@PathParam("pair") Pair pair)
      throws IOException, BitstampException;

  @GET
  @Path("ticker/{pair}/")
  BitstampTicker getTicker(@PathParam("pair") BitstampV2.Pair pair)
      throws IOException, BitstampException;

  /** Returns descending list of transactions. */
  @GET
  @Path("transactions/{pair}/")
  BitstampTransaction[] getTransactions(
      @PathParam("pair") Pair pair,
      @QueryParam("time") BitstampMarketDataServiceRaw.BitstampTime time)
      throws IOException, BitstampException;

  class Pair {
    public final CurrencyPair pair;

    public Pair(CurrencyPair pair) {
      this.pair = pair;
    }

    public Pair(String pair) {
      this(CurrencyPairDeserializer.getCurrencyPairFromString(pair));
    }

    @Override
    public boolean equals(Object o) {
      return this == o
          || !(o == null || getClass() != o.getClass()) && Objects.equals(pair, ((Pair) o).pair);
    }

    @Override
    public int hashCode() {
      return Objects.hash(pair);
    }

    @Override
    public String toString() {
      return pair == null
          ? ""
          : String.format(
              "%s%s",
              pair.base.getCurrencyCode().toLowerCase(),
              pair.counter.getCurrencyCode().toLowerCase());
    }
  }
}
