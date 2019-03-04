package com.qidiancamp.test.bitstamp;

import com.qidiancamp.Exchange;
import com.qidiancamp.ExchangeFactory;
import com.qidiancamp.api.bitstamp.BitstampAdapters;
import com.qidiancamp.api.bitstamp.dto.account.BitstampBalance;
import com.qidiancamp.api.bitstamp.dto.marketdata.BitstampTicker;
import com.qidiancamp.api.bitstamp.service.BitstampAccountService;
import com.qidiancamp.api.gate.GateioExchange;
import com.qidiancamp.currency.CurrencyPair;
import com.qidiancamp.dto.marketdata.Ticker;
import com.qidiancamp.service.marketdata.MarketDataService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {

    @Autowired
    private BitstampAccountService bitstampAccountService;

//    @Test
//    public void accountInfo() throws IOException {
//
//        List<CurrencyPair> currencyPairs =
//                Stream.of(CurrencyPair.BTC_USD, CurrencyPair.ETH_USD, CurrencyPair.ETH_BTC)
//                        .collect(Collectors.toList());
//        BitstampBalance balance = new BitstampBalance();
//        String formattedPairs = BitstampAdapters.adaptAccountInfo(currencyPairs);
//        Assert.assertEquals("tBTCUSD,tETHUSD,tETHBTC", formattedPairs);
//    }

    @Test
    public void tickerFetchTest() throws Exception {

        Exchange exchange = ExchangeFactory.INSTANCE.createExchange(GateioExchange.class.getName());
        MarketDataService marketDataService = exchange.getMarketDataService();
        Ticker ticker = marketDataService.getTicker(new CurrencyPair("BTC", "USDT"));
        System.out.println(ticker.toString());
        assertThat(ticker).isNotNull();
    }

}
