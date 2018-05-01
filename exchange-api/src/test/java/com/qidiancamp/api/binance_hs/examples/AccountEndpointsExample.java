package com.qidiancamp.api.binance_hs.examples;

import com.qidiancamp.api.binance_hs.BinanceApiClientFactory;
import com.qidiancamp.api.binance_hs.BinanceApiRestClient;
import com.qidiancamp.api.binance_hs.domain.account.Account;
import com.qidiancamp.api.binance_hs.domain.account.Trade;

import java.util.List;

/**
 * Examples on how to get account information.
 */
public class AccountEndpointsExample {

  public static void main(String[] args) {
    BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance("8GOySnT93z9lo8mxTFDQNBbBtuaqoKV1f5FzHwqINLAFqtrV2yTkCSWnbErHVzjI", "5i2KgN6jfMDhtwqLf0ysW9kV0q2UNsgXq5PUS3zeRlrVkEOqbszuAHve6ICJ85FW");
    BinanceApiRestClient client = factory.newRestClient();

    // Get account balances
    Account account = client.getAccount(6000000L, System.currentTimeMillis());
    System.out.println(account.getBalances());
    System.out.println(account.getAssetBalance("ETH"));

    // Get list of trades
    List<Trade> myTrades = client.getMyTrades("NEOETH");
    System.out.println(myTrades);

    // Get withdraw history
    System.out.println(client.getWithdrawHistory("ETH"));

    // Get deposit history
    System.out.println(client.getDepositHistory("ETH"));

    // Get deposit address
    System.out.println(client.getDepositAddress("ETH"));

    // Withdraw
    client.withdraw("ETH", "0x123", "0.1", null);
  }
}
