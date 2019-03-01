package com.qidiancamp.api.binance_hs.client.impl;

import com.qidiancamp.api.binance_hs.impl.BinanceApiService;
import com.qidiancamp.api.binance_hs.impl.BinanceApiServiceGenerator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author andy coates
 * created 08/02/2018.
 */
public class BinanceApiServiceGeneratorTest {
    @Test
    public void shouldOnlyAddAuthInterceptorOnce() throws Exception {
        // Given:
        BinanceApiServiceGenerator.createService(BinanceApiService.class, "8GOySnT93z9lo8mxTFDQNBbBtuaqoKV1f5FzHwqINLAFqtrV2yTkCSWnbErHVzjI", "5i2KgN6jfMDhtwqLf0ysW9kV0q2UNsgXq5PUS3zeRlrVkEOqbszuAHve6ICJ85FW");
        final int initialSize = BinanceApiServiceGenerator.httpClient.interceptors().size();

        // When:
        BinanceApiServiceGenerator.createService(BinanceApiService.class, "8GOySnT93z9lo8mxTFDQNBbBtuaqoKV1f5FzHwqINLAFqtrV2yTkCSWnbErHVzjI", "5i2KgN6jfMDhtwqLf0ysW9kV0q2UNsgXq5PUS3zeRlrVkEOqbszuAHve6ICJ85FW");

        // Then
        assertEquals(BinanceApiServiceGenerator.httpClient.interceptors().size(), initialSize);
    }
}