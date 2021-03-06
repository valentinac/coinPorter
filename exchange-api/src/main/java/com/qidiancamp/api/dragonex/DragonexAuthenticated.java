package com.qidiancamp.api.dragonex;

import com.qidiancamp.api.dragonex.dto.DragonResult;
import com.qidiancamp.api.dragonex.dto.DragonexException;
import com.qidiancamp.api.dragonex.dto.Token;
import com.qidiancamp.api.dragonex.dto.TokenStatus;
import com.qidiancamp.api.dragonex.dto.account.Balance;
import com.qidiancamp.api.dragonex.dto.trade.*;
import si.mazi.rescu.ParamsDigest;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

/** https://github.com/Dragonexio/OpenApi/blob/master/docs/English/1.interface_document_v1.md */
@Path("api/v1")
@Consumes(MediaType.APPLICATION_JSON)
public interface DragonexAuthenticated {

  /** Create new token */
  @POST
  @Path("token/new/")
  DragonResult<Token> tokenNew(
          @HeaderParam("date") String date,
          @HeaderParam("auth") ParamsDigest auth,
          @HeaderParam("content-sha1") ParamsDigest contentSHA1)
      throws DragonexException, IOException;

  /** Token status */
  @POST
  @Path("token/status/")
  DragonResult<TokenStatus> tokenStatus(
          @HeaderParam("date") String date,
          @HeaderParam("token") String token,
          @HeaderParam("auth") ParamsDigest auth,
          @HeaderParam("content-sha1") ParamsDigest contentSHA1)
      throws DragonexException, IOException;

  /** Query all coins you own */
  @POST
  @Path("user/own/")
  DragonResult<List<Balance>> userCoins(
          @HeaderParam("date") String date,
          @HeaderParam("token") String token,
          @HeaderParam("auth") ParamsDigest auth,
          @HeaderParam("content-sha1") ParamsDigest contentSHA1)
      throws DragonexException, IOException;

  /** Add new buy order */
  @POST
  @Path("order/buy/")
  DragonResult<UserOrder> orderBuy(
          @HeaderParam("date") String date,
          @HeaderParam("token") String token,
          @HeaderParam("auth") ParamsDigest auth,
          @HeaderParam("Content-Sha1") ParamsDigest contentSHA1,
          OrderPlacement orderPlacement)
      throws DragonexException, IOException;

  /** Add new sell order */
  @POST
  @Path("order/sell/")
  DragonResult<UserOrder> orderSell(
          @HeaderParam("date") String date,
          @HeaderParam("token") String token,
          @HeaderParam("auth") ParamsDigest auth,
          @HeaderParam("Content-Sha1") ParamsDigest contentSHA1,
          OrderPlacement orderPlacement)
      throws DragonexException, IOException;

  /** Cancel Order */
  @POST
  @Path("order/cancel/")
  DragonResult<UserOrder> orderCancel(
          @HeaderParam("date") String date,
          @HeaderParam("token") String token,
          @HeaderParam("auth") ParamsDigest auth,
          @HeaderParam("Content-Sha1") ParamsDigest contentSHA1,
          OrderReference ref)
      throws DragonexException, IOException;

  /** Request details of user’s delegation records */
  @POST
  @Path("order/detail/")
  DragonResult<UserOrder> orderDetail(
          @HeaderParam("date") String date,
          @HeaderParam("token") String token,
          @HeaderParam("auth") ParamsDigest auth,
          @HeaderParam("Content-Sha1") ParamsDigest contentSHA1,
          OrderReference ref)
      throws DragonexException, IOException;

  /** Request user’s delegation records */
  @POST
  @Path("order/history/")
  DragonResult<OrderHistory> orderHistory(
          @HeaderParam("date") String date,
          @HeaderParam("token") String token,
          @HeaderParam("auth") ParamsDigest auth,
          @HeaderParam("Content-Sha1") ParamsDigest contentSHA1,
          OrderHistoryRequest orderHistory)
      throws DragonexException, IOException;

  /** Request user’s records of successful trade */
  @POST
  @Path("deal/history/")
  DragonResult<DealHistory> dealHistory(
          @HeaderParam("date") String date,
          @HeaderParam("token") String token,
          @HeaderParam("auth") ParamsDigest auth,
          @HeaderParam("Content-Sha1") ParamsDigest contentSHA1,
          DealHistoryRequest orderHistory)
      throws DragonexException, IOException;
}
