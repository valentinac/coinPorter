package com.qidiancamp.dto;


import java.math.BigDecimal;

/**
 *  author: valentina@2018.4.27
 *  price difference for exchange
 */
public class BlockInfo {

    private String coin_name;          //币种的名称
    private String coin_abbr;          //币种缩写
    private BigDecimal price_A;        //交易所A价格
    private BigDecimal price_B;        //交易所B价格
    private Float diff;                //差价
    private Float bid_ask;             // 买卖百分比

    public String getCoin_name() {
        return coin_name;
    }

    public void setCoin_name(String coin_name) {
        this.coin_name = coin_name;
    }

    public String getCoin_abbr() {
        return coin_abbr;
    }

    public void setCoin_abbr(String coin_abbr) {
        this.coin_abbr = coin_abbr;
    }

    public BigDecimal getPrice_A() {
        return price_A;
    }

    public void setPrice_A(BigDecimal price_A) {
        this.price_A = price_A;
    }

    public BigDecimal getPrice_B() {
        return price_B;
    }

    public void setPrice_B(BigDecimal price_B) {
        this.price_B = price_B;
    }

    public Float getDiff() {
        return diff;
    }

    public void setDiff(Float diff) {
        this.diff = diff;
    }

    public Float getBid_ask() {
        return bid_ask;
    }

    public void setBid_ask(Float bid_ask) {
        this.bid_ask = bid_ask;
    }
}
