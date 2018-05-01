package com.qidiancamp.api.huobi;

import org.junit.Ignore;
import org.junit.Test;
import com.qidiancamp.Exchange;
import com.qidiancamp.ExchangeFactory;
import com.qidiancamp.currency.Currency;
import com.qidiancamp.currency.CurrencyPair;
import com.qidiancamp.dto.Order;
import com.qidiancamp.dto.Order.OrderType;
import com.qidiancamp.dto.account.Balance;
import com.qidiancamp.dto.trade.LimitOrder;
import com.qidiancamp.dto.trade.MarketOrder;
import com.qidiancamp.dto.trade.OpenOrders;
import com.qidiancamp.api.huobi.dto.account.HuobiAccount;
import com.qidiancamp.api.huobi.service.HuobiAccountService;
import com.qidiancamp.service.account.AccountService;
import com.qidiancamp.service.trade.TradeService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@Ignore("Use it for manual launch only")
public class HuobiPrivateApiIntegration {

  @Test
  public void getAccountTest() throws IOException {
    HuobiProperties properties = new HuobiProperties();
    Exchange exchange =
        ExchangeFactory.INSTANCE.createExchange(
            HuobiExchange.class.getName(), properties.getApiKey(), properties.getSecretKey());
    HuobiAccountService accountService = (HuobiAccountService) exchange.getAccountService();
    HuobiAccount[] accounts = accountService.getAccounts();
    System.out.println(Arrays.toString(accounts));
  }

  @Test
  public void getBalanceTest() throws IOException {
    HuobiProperties properties = new HuobiProperties();
    Exchange exchange =
        ExchangeFactory.INSTANCE.createExchange(
            HuobiExchange.class.getName(), properties.getApiKey(), properties.getSecretKey());
    AccountService accountService = exchange.getAccountService();
    Balance balance = accountService.getAccountInfo().getWallet().getBalance(Currency.USDT);
    System.out.println(balance.toString());
    assertThat(balance).isNotNull();
  }

  @Test
  public void getOpenOrdersTest() throws IOException {
    HuobiProperties properties = new HuobiProperties();
    Exchange exchange =
        ExchangeFactory.INSTANCE.createExchange(
            HuobiExchange.class.getName(), properties.getApiKey(), properties.getSecretKey());
    TradeService tradeService = exchange.getTradeService();
    OpenOrders openOrders = tradeService.getOpenOrders();
    System.out.println(openOrders.toString());
    assertThat(openOrders).isNotNull();
  }

  @Test
  public void getOrderTest() throws IOException {
    HuobiProperties properties = new HuobiProperties();
    Exchange exchange =
        ExchangeFactory.INSTANCE.createExchange(
            HuobiExchange.class.getName(), properties.getApiKey(), properties.getSecretKey());
    TradeService tradeService = exchange.getTradeService();
    Collection<Order> orders = tradeService.getOrder("2132866355");
    System.out.println(orders.toString());
    assertThat(orders).isNotNull();
  }

  @Test
  public void placeLimitOrderTest() throws IOException {
    String orderId = placePendingOrder();
    System.out.println(orderId);
  }

  private String placePendingOrder() throws IOException {
    HuobiProperties properties = new HuobiProperties();
    Exchange exchange =
        ExchangeFactory.INSTANCE.createExchange(
            HuobiExchange.class.getName(), properties.getApiKey(), properties.getSecretKey());
    TradeService tradeService = exchange.getTradeService();
    HuobiAccountService accountService = (HuobiAccountService) exchange.getAccountService();
    HuobiAccount[] accounts = accountService.getAccounts();
    LimitOrder limitOrder =
        new LimitOrder(
            OrderType.BID,
            new BigDecimal("0.001"),
            new CurrencyPair("BTC", "USDT"),
            String.valueOf(accounts[0].getId()),
            null,
            new BigDecimal("10000"));
    return tradeService.placeLimitOrder(limitOrder);
  }

  @Test
  public void placeMarketOrderTest() throws IOException {
    HuobiProperties properties = new HuobiProperties();
    Exchange exchange =
        ExchangeFactory.INSTANCE.createExchange(
            HuobiExchange.class.getName(), properties.getApiKey(), properties.getSecretKey());
    TradeService tradeService = exchange.getTradeService();
    HuobiAccountService accountService = (HuobiAccountService) exchange.getAccountService();
    HuobiAccount[] accounts = accountService.getAccounts();
    MarketOrder marketOrder =
        new MarketOrder(
            OrderType.BID,
            new BigDecimal("0.001"),
            new CurrencyPair("BTC", "USDT"),
            String.valueOf(accounts[0].getId()),
            null);
    String orderId = tradeService.placeMarketOrder(marketOrder);
    System.out.println(orderId);
  }

  @Test
  @Ignore("Use it for manual")
  public void cancelOrderTest() throws IOException {
    HuobiProperties properties = new HuobiProperties();
    Exchange exchange =
        ExchangeFactory.INSTANCE.createExchange(
            HuobiExchange.class.getName(), properties.getApiKey(), properties.getSecretKey());
    TradeService tradeService = exchange.getTradeService();
    boolean result = tradeService.cancelOrder("2134551697");
    System.out.println(result);
  }
}
