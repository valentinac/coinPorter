package com.qidiancamp.api.binance_hs.examples;

import com.qidiancamp.api.binance_hs.BinanceApiClientFactory;
import com.qidiancamp.api.binance_hs.BinanceApiRestClient;
import com.qidiancamp.api.binance_hs.BinanceApiWebSocketClient;
import com.qidiancamp.api.binance_hs.domain.event.AccountUpdateEvent;
import com.qidiancamp.api.binance_hs.domain.event.OrderTradeUpdateEvent;
import com.qidiancamp.api.binance_hs.domain.event.UserDataUpdateEvent.UserDataUpdateEventType;

/**
 * User data stream endpoints examples.
 *
 * It illustrates how to create a stream to obtain updates on a user account,
 * as well as update on trades/orders on a user account.
 */
public class UserDataStreamExample {

  public static void main(String[] args) {
    BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance("UFyg0elVw4mBQKptheHLERc407118mi52xCkCY03UoUHW96efFj8uTMKnRoVX8RU", "BpPoHmikcIYW5KNCBTSP1YxGFXGVVI7Jo7tsGy3JonYC5luuJ1uYW9O5PQUwr9Ne");
    BinanceApiRestClient client = factory.newRestClient();

    // First, we obtain a listenKey which is required to interact with the user data stream
    String listenKey = client.startUserDataStream();

    // Then, we open a new web socket client, and provide a callback that is called on every update
    BinanceApiWebSocketClient webSocketClient = factory.newWebSocketClient();

    // Listen for changes in the account
    webSocketClient.onUserDataUpdateEvent(listenKey, response -> {
      if (response.getEventType() == UserDataUpdateEventType.ACCOUNT_UPDATE) {
        AccountUpdateEvent accountUpdateEvent = response.getAccountUpdateEvent();
        // Print new balances of every available asset
        System.out.println(accountUpdateEvent.getBalances());
      } else {
        OrderTradeUpdateEvent orderTradeUpdateEvent = response.getOrderTradeUpdateEvent();
        // Print details about an order/trade
        System.out.println(orderTradeUpdateEvent);

        // Print original quantity
        System.out.println(orderTradeUpdateEvent.getOriginalQuantity());

        // Or price
        System.out.println(orderTradeUpdateEvent.getPrice());
      }
    });
    System.out.println("Waiting for events...");

    // We can keep alive the user data stream
    // client.keepAliveUserDataStream(listenKey);

    // Or we can invalidate it, whenever it is no longer needed
    // client.closeUserDataStream(listenKey);
  }
}
