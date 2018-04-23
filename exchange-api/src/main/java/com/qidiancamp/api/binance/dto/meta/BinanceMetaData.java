package com.qidiancamp.api.binance.dto.meta;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.qidiancamp.currency.Currency;
import com.qidiancamp.currency.CurrencyPair;
import com.qidiancamp.dto.meta.CurrencyMetaData;
import com.qidiancamp.dto.meta.CurrencyPairMetaData;
import com.qidiancamp.dto.meta.ExchangeMetaData;
import com.qidiancamp.dto.meta.RateLimit;

import java.util.Map;

public class BinanceMetaData extends ExchangeMetaData {

  public BinanceMetaData(@JsonProperty("currency_pairs") Map<CurrencyPair, CurrencyPairMetaData> currencyPairs,
      @JsonProperty("currencies") Map<Currency, CurrencyMetaData> currencies,
      @JsonProperty("public_rate_limits") RateLimit[] publicRateLimits,
      @JsonProperty("private_rate_limits") RateLimit[] privateRateLimits,
      @JsonProperty("share_rate_limits") Boolean shareRateLimits) {
    super(currencyPairs, currencies, publicRateLimits, privateRateLimits, shareRateLimits);
  }
}
