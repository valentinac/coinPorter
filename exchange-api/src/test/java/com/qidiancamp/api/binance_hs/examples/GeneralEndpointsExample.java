package com.qidiancamp.api.binance_hs.examples;

import com.qidiancamp.api.binance_hs.BinanceApiClientFactory;
import com.qidiancamp.api.binance_hs.BinanceApiRestClient;
import com.qidiancamp.api.binance_hs.domain.general.*;

import java.util.List;

/**
 * Examples on how to use the general endpoints.
 */
public class GeneralEndpointsExample {

  public static void main(String[] args) {
    BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance();
    BinanceApiRestClient client = factory.newRestClient();

    // Test connectivity
    client.ping();

    // Check server time
    long serverTime = client.getServerTime();
    System.out.println(serverTime);

    // Exchange info
    ExchangeInfo exchangeInfo = client.getExchangeInfo();
    System.out.println(exchangeInfo.getTimezone());
    System.out.println(exchangeInfo.getSymbols());

    // Obtain symbol information
    SymbolInfo symbolInfo = exchangeInfo.getSymbolInfo("ETHBTC");
    System.out.println(symbolInfo.getStatus());

    SymbolFilter priceFilter = symbolInfo.getSymbolFilter(FilterType.PRICE_FILTER);
    System.out.println(priceFilter.getMinPrice());
    System.out.println(priceFilter.getTickSize());

    // Obtain asset information
    List<Asset> allAssets = client.getAllAssets();
    System.out.println(allAssets.stream().filter(asset -> asset.getAssetCode().equals("BNB")).findFirst().get());
  }
}
