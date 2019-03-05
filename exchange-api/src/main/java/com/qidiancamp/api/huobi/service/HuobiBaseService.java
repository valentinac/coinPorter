package com.qidiancamp.api.huobi.service;

import com.qidiancamp.Exchange;
import com.qidiancamp.exceptions.ExchangeException;
import com.qidiancamp.api.huobi.Huobi;
import com.qidiancamp.api.huobi.dto.HuobiResult;
import com.qidiancamp.api.huobi.dto.marketdata.HuobiAsset;
import com.qidiancamp.api.huobi.dto.marketdata.results.HuobiAssetsResult;
import com.qidiancamp.service.BaseExchangeService;
import com.qidiancamp.service.BaseService;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import java.io.IOException;

public class HuobiBaseService extends BaseExchangeService implements BaseService {

  protected Huobi huobi;
  protected ParamsDigest signatureCreator;

  public HuobiBaseService(Exchange exchange) {
//    super(exchange);
    huobi =
        RestProxyFactory.createProxy(
            Huobi.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());
    signatureCreator =
        HuobiDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

  protected <R> R checkResult(HuobiResult<R> huobiResult) {
    if (!huobiResult.isSuccess()) {
      String huobiError = huobiResult.getError();
      if (huobiError.length() == 0) {
        throw new ExchangeException("Missing error message");
      } else {
        throw new ExchangeException(huobiError);
      }
    }
    return huobiResult.getResult();
  }

  public HuobiAsset[] getHuobiAssets() throws IOException {
    HuobiAssetsResult assetsResult = huobi.getAssets();
    return checkResult(assetsResult);
  }
}
