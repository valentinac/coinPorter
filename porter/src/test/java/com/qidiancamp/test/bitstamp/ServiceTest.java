package com.qidiancamp.test.bitstamp;

import com.qidiancamp.api.bitstamp.BitstampAdapters;
import com.qidiancamp.api.bitstamp.dto.account.BitstampBalance;
import com.qidiancamp.api.bitstamp.dto.marketdata.BitstampTicker;
import com.qidiancamp.api.bitstamp.service.BitstampAccountService;
import com.qidiancamp.currency.CurrencyPair;
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

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {

    @Autowired
    private BitstampAccountService bitstampAccountService;

    @Test
    public void accountInfo() throws IOException {

        List<CurrencyPair> currencyPairs =
                Stream.of(CurrencyPair.BTC_USD, CurrencyPair.ETH_USD, CurrencyPair.ETH_BTC)
                        .collect(Collectors.toList());
        BitstampBalance balance = new BitstampBalance();
        String formattedPairs = BitstampAdapters.adaptAccountInfo(currencyPairs);
        Assert.assertEquals("tBTCUSD,tETHUSD,tETHBTC", formattedPairs);
    }

}
