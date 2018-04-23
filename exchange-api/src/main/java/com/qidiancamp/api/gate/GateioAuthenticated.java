package com.qidiancamp.api.gate;

import com.qidiancamp.api.GateioBaseResponse;
import com.qidiancamp.api.gate.dto.account.GateioFunds;
import com.qidiancamp.api.gate.dto.trade.GateioOpenOrders;
import com.qidiancamp.api.gate.dto.trade.GateioOrderStatus;
import com.qidiancamp.api.gate.dto.trade.GateioPlaceOrderReturn;
import com.qidiancamp.api.gate.dto.trade.GateioTradeHistoryReturn;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.math.BigDecimal;

@Path("api2/1")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public interface GateioAuthenticated extends Gateio {

  @POST
  @Path("private/balances")
  GateioFunds getFunds(@HeaderParam("KEY") String apiKey, @HeaderParam("SIGN") ParamsDigest signer,
                       @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

  @POST
  @Path("private/cancelorder")
  GateioBaseResponse cancelOrder(@FormParam("orderNumber") String orderNumber, @HeaderParam("KEY") String apiKey,
                                 @HeaderParam("SIGN") ParamsDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

  @POST
  @Path("private/cancelAllOrders")
  GateioBaseResponse cancelAllOrders(@FormParam("type") String type, @FormParam("currencyPair") String currencyPair, @HeaderParam("KEY") String apiKey,
                                     @HeaderParam("SIGN") ParamsDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

  @POST
  @Path("private/buy")
  GateioPlaceOrderReturn buy(@FormParam("currencyPair") String currencyPair, @FormParam("rate") BigDecimal rate,
                             @FormParam("amount") BigDecimal amount, @HeaderParam("KEY") String apiKey, @HeaderParam("SIGN") ParamsDigest signer,
                             @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

  @POST
  @Path("private/sell")
  GateioPlaceOrderReturn sell(@FormParam("currencyPair") String currencyPair, @FormParam("rate") BigDecimal rate,
                              @FormParam("amount") BigDecimal amount, @HeaderParam("KEY") String apiKey, @HeaderParam("SIGN") ParamsDigest signer,
                              @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

  @POST
  @Path("private/openOrders")
  GateioOpenOrders getOpenOrders(@HeaderParam("KEY") String apiKey, @HeaderParam("SIGN") ParamsDigest signer,
                                 @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;

  @POST
  @Path("private/tradeHistory")
  GateioTradeHistoryReturn getUserTradeHistory(@HeaderParam("KEY") String apiKey, @HeaderParam("SIGN") ParamsDigest signer,
                                               @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("pair") String pair) throws IOException;

  @POST
  @Path("private/getorder")
  GateioOrderStatus getOrderStatus(@FormParam("order_id") String orderId, @HeaderParam("KEY") String apiKey, @HeaderParam("SIGN") ParamsDigest signer,
                                   @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException;
}
