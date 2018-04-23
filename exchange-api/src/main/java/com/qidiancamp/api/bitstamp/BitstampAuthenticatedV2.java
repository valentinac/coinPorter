package com.qidiancamp.api.bitstamp;

import com.qidiancamp.api.bitstamp.dto.BitstampException;
import com.qidiancamp.api.bitstamp.dto.account.BitstampWithdrawal;
import com.qidiancamp.api.bitstamp.dto.trade.BitstampOrder;
import com.qidiancamp.api.bitstamp.dto.trade.BitstampUserTransaction;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.math.BigDecimal;

@Path("api/v2")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public interface BitstampAuthenticatedV2 {

  @POST
  @Path("open_orders/{pair}/")
  BitstampOrder[] getOpenOrders(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
                                @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @PathParam("pair") BitstampV2.Pair pair) throws BitstampException, IOException;

  @POST
  @Path("{side}/market/{pair}/")
  BitstampOrder placeMarketOrder(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
                                 @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @PathParam("side") Side side, @PathParam("pair") BitstampV2.Pair pair,
                                 @FormParam("amount") BigDecimal amount) throws BitstampException, IOException;

  @POST
  @Path("{side}/{pair}/")
  BitstampOrder placeOrder(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
                           @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @PathParam("side") Side side, @PathParam("pair") BitstampV2.Pair pair,
                           @FormParam("amount") BigDecimal amount, @FormParam("price") BigDecimal price) throws BitstampException, IOException;

  @POST
  @Path("user_transactions/")
  BitstampUserTransaction[] getUserTransactions(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
                                                @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("limit") Long numberOfTransactions, @FormParam("offset") Long offset,
                                                @FormParam("sort") String sort) throws BitstampException, IOException;

  @POST
  @Path("user_transactions/{pair}/")
  BitstampUserTransaction[] getUserTransactions(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
                                                @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @PathParam("pair") BitstampV2.Pair pair,
                                                @FormParam("limit") Long numberOfTransactions, @FormParam("offset") Long offset,
                                                @FormParam("sort") String sort) throws BitstampException, IOException;

  enum Side {
    buy, sell
  }

  @POST
  @Path("xrp_withdrawal/")
  BitstampWithdrawal xrpWithdrawal(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
                                   @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("amount") BigDecimal amount,
                                   @FormParam("address") String rippleAddress, @FormParam("destination_tag") String destinationTag) throws BitstampException, IOException;

  @POST
  @Path("ltc_withdrawal/")
  BitstampWithdrawal withdrawLitecoin(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
                                      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("amount") BigDecimal amount,
                                      @FormParam("address") String address) throws BitstampException, IOException;

  @POST
  @Path("bch_withdrawal/")
  BitstampWithdrawal bchWithdrawal(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
                                   @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("amount") BigDecimal amount,
                                   @FormParam("address") String address) throws BitstampException, IOException;
  
  @POST
  @Path("eth_withdrawal/")
  BitstampWithdrawal withdrawEther(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
                                   @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("amount") BigDecimal amount,
                                   @FormParam("address") String address) throws BitstampException, IOException;

}
