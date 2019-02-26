package com.qidiancamp.api.bithumb;

import com.qidiancamp.api.bithumb.impl.Api_Client;
import java.util.HashMap;

public class BithumbClientTest {
  public static void main(String args[]) {
    Api_Client api = new Api_Client("24b4daec646335f9b977c7ed0a7962f6", "7761fde325553d3ca0cb53586199bafe");

    HashMap<String, String> rgParams = new HashMap<String, String>();
    rgParams.put("order_currency", "BTC");
    rgParams.put("payment_currency", "KRW");

    try {
      String result = api.callApi("/info/balance", rgParams);
      System.out.println(result);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
