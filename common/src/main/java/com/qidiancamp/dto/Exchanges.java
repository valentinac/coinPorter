package com.qidiancamp.dto;


/**
 * author:valentina@2017.4.27
 * 支持的交易所列表
 */
public enum Exchanges {


    Bitstamp("bitstamp", "bitstamp"),
    BINANCE("binance", "币安"),
    GATE("gate", "比特儿"),
    BITHUMB("bithumb","bithumb"),
    DRAGONEX("dragonex", "龙网");

    private String name;              //交易所名称
    private String cnName;            //交易所中文名

    // 构造方法
    private Exchanges(String name, String cnName) {
        this.name = name;
        this.cnName = cnName;
    }
    // 普通方法
    public static String getCnName(String name) {
        for (Exchanges e : Exchanges.values()) {
            if (e.getName() == name) {
                return e.cnName;
            }
        }
        return null;
    }

    Exchanges(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }



}
