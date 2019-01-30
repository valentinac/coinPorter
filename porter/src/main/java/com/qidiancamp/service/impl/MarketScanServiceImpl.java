package com.qidiancamp.service.impl;

import com.qidiancamp.api.bitstamp.BitstampExchange;
import com.qidiancamp.dto.marketdata.Ticker;
import com.qidiancamp.entity.MarketPipeline;
import com.qidiancamp.service.IMarketScanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class MarketScanServiceImpl implements IMarketScanService {

    Logger logger = LoggerFactory.getLogger(MarketScanServiceImpl.class);

    @Override
    public List<MarketPipeline> getMarketPipelineList() {
        BitstampExchange binanceExchange = new BitstampExchange();
        try {
            List<Ticker> bian_ticker = binanceExchange.getMarketDataService().getTickers(null);
            for (Ticker t:bian_ticker){
                logger.info(""+t.getAsk());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
