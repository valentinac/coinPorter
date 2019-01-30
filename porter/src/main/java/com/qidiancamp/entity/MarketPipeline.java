package com.qidiancamp.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 价格管道
 */
public class MarketPipeline implements Serializable {
    private Integer id;
    private String exchange_low;                    //低价交易所名称
    private BigDecimal price_low;                   //低价价格
    private String exchange_high;                   //高价交易所名称
    private BigDecimal price_high;                  //高价价格
    private String coin_name;                       //币种名称
    private Float spread_rate;                      //差额比例

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExchange_low() {
        return exchange_low;
    }

    public void setExchange_low(String exchange_low) {
        this.exchange_low = exchange_low;
    }

    public BigDecimal getPrice_low() {
        return price_low;
    }

    public void setPrice_low(BigDecimal price_low) {
        this.price_low = price_low;
    }

    public String getExchange_high() {
        return exchange_high;
    }

    public void setExchange_high(String exchange_high) {
        this.exchange_high = exchange_high;
    }

    public BigDecimal getPrice_high() {
        return price_high;
    }

    public void setPrice_high(BigDecimal price_high) {
        this.price_high = price_high;
    }

    public String getCoin_name() {
        return coin_name;
    }

    public void setCoin_name(String coin_name) {
        this.coin_name = coin_name;
    }

    public Float getSpread_rate() {
        return spread_rate;
    }

    public void setSpread_rate(Float spread_rate) {
        this.spread_rate = spread_rate;
    }
}
