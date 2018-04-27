package com.qidiancamp.porterbatch.trade.binanace.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import com.qidiancamp.api.gate.dto.GateioOrderType;
import com.qidiancamp.api.gate.dto.trade.GateioOpenOrder;
import com.qidiancamp.api.gate.dto.trade.GateioOpenOrders;
import com.qidiancamp.api.gate.dto.trade.GateioOrderStatus;
import com.qidiancamp.api.gate.dto.trade.GateioPlaceOrderReturn;
import com.qidiancamp.currency.CurrencyPair;
import org.assertj.core.api.Assertions;
import org.junit.Test;
public class GateioTradeJsonTest {

  @Test
  public void testDeserializeOrderList() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        GateioTradeJsonTest.class.getResourceAsStream("/trade/example-order-list-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    GateioOpenOrders openOrders = mapper.readValue(is, GateioOpenOrders.class);

    assertThat(openOrders.isResult()).isTrue();
    assertThat(openOrders.getMessage()).isEqualTo("Success");

    List<GateioOpenOrder> openOrderList = openOrders.getOrders();
    Assertions.assertThat(openOrderList).hasSize(2);

    GateioOpenOrder openOrder = openOrderList.get(0);
    assertThat(openOrder.getId()).isEqualTo("3");
    assertThat(openOrder.getCurrencyPair().split("_")[0]).isEqualTo("eth");
    assertThat(openOrder.getCurrencyPair().split("_")[1]).isEqualTo("btc");
    assertThat(openOrder.getAmount()).isEqualTo(new BigDecimal("100000"));
  }

  @Test
  public void testDeserializeOrderResult() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        GateioTradeJsonTest.class.getResourceAsStream("/trade/example-order-result-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    GateioPlaceOrderReturn orderReturn = mapper.readValue(is, GateioPlaceOrderReturn.class);

    assertThat(orderReturn.isResult()).isTrue();
    assertThat(orderReturn.getMessage()).isEqualTo("Success");
    assertThat(orderReturn.getOrderId()).isEqualTo("123456");
  }

  @Test
  public void testDeserializeOrderStatus() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        GateioTradeJsonTest.class.getResourceAsStream("/trade/example-order-status-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    GateioOrderStatus orderStatus = mapper.readValue(is, GateioOrderStatus.class);

    assertThat(orderStatus.isResult()).isTrue();
    assertThat(orderStatus.getMessage()).isEqualTo("Success");
    assertThat(orderStatus.getId()).isEqualTo("12942570");
    assertThat(orderStatus.getStatus()).isEqualTo("open");
    assertThat(orderStatus.getCurrencyPair()).isEqualTo(CurrencyPair.LTC_BTC);
    assertThat(orderStatus.getType()).isEqualTo(GateioOrderType.SELL);
    assertThat(orderStatus.getRate()).isEqualTo("0.0265");
    assertThat(orderStatus.getAmount()).isEqualTo("0.384");
    assertThat(orderStatus.getInitialRate()).isEqualTo("0.0265");
    assertThat(orderStatus.getInitialAmount()).isEqualTo("0.384");
  }
}
