package com.qidiancamp.api.coinone.service;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestInvocation;

import java.util.Base64;

public class CoinonePayloadDigest implements ParamsDigest {

  private CoinonePayloadDigest() {}

  public static CoinonePayloadDigest createInstance() {

    return new CoinonePayloadDigest();
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    String postBody = restInvocation.getRequestBody();
    String payload = Base64.getEncoder().encodeToString(postBody.getBytes());
    return payload;
  }
}
