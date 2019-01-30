package com.qidiancamp.service;


import com.qidiancamp.entity.MarketPipeline;

import java.util.List;

public interface IMarketScanService {

    /**
     * 获取交易所差额列表
     * @return
     */
    public List<MarketPipeline> getMarketPipelineList();
}
