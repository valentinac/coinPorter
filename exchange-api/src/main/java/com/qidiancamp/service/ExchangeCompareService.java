package com.qidiancamp.service;


import com.qidiancamp.dto.BlockInfo;
import com.qidiancamp.dto.Exchanges;

import java.util.ArrayList;
import java.util.List;

/**
 * author: valentina@2018.4.27
 * 获取不同交易所的差价对比
 */
public class ExchangeCompareService {

    public List<BlockInfo> getBlockDiffs(String exchangeA,String exchangeB) throws Exception {
        List<BlockInfo> res = new ArrayList<BlockInfo>();
        //排除参数异常的情况
        if(Exchanges.getCnName(exchangeA)==null || Exchanges.getCnName(exchangeB)==null){
            throw new Exception("交易所信息错误");
        }
//        List coinListA = BinanceExchange

        return res;
    }
}
