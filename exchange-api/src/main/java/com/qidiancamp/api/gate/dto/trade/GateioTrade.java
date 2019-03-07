package com.qidiancamp.api.gate.dto.trade;

import com.fasterxml.jackson.annotation.*;
import com.qidiancamp.api.gate.dto.GateioOrderType;

import javax.annotation.Generated;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({"tradeID", "orderNumber", "pair", "type", "rate", "amount", "time_unix"})
public class GateioTrade {

  @JsonProperty("tradeID")
  private String tradeID;

  @JsonProperty("orderNumber")
  private String orderNumber;

  @JsonProperty("pair")
  private String pair;

  @JsonProperty("type")
  private GateioOrderType type;

  @JsonProperty("rate")
  private BigDecimal rate;

  @JsonProperty("amount")
  private BigDecimal amount;

  @JsonProperty("time_unix")
  private long timeUnix;

  @JsonIgnore private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  @JsonProperty("tradeID")
  public String getTradeID() {
    return tradeID;
  }

  @JsonProperty("tradeID")
  public void setTradeID(String tradeID) {
    this.tradeID = tradeID;
  }

  @JsonProperty("orderNumber")
  public String getOrderNumber() {
    return orderNumber;
  }

  @JsonProperty("orderNumber")
  public void setOrderNumber(String orderNumber) {
    this.orderNumber = orderNumber;
  }

  @JsonProperty("pair")
  public String getPair() {
    return pair;
  }

  @JsonProperty("pair")
  public void setPair(String pair) {
    this.pair = pair;
  }

  @JsonProperty("type")
  public GateioOrderType getType() {
    return type;
  }

  @JsonProperty("type")
  public void setType(GateioOrderType type) {
    this.type = type;
  }

  @JsonProperty("rate")
  public BigDecimal getRate() {
    return rate;
  }

  @JsonProperty("rate")
  public void setRate(BigDecimal rate) {
    this.rate = rate;
  }

  @JsonProperty("amount")
  public BigDecimal getAmount() {
    return amount;
  }

  @JsonProperty("amount")
  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  @JsonProperty("time_unix")
  public long getTimeUnix() {
    return timeUnix;
  }

  @JsonProperty("time_unix")
  public void setTimeUnix(long timeUnix) {
    this.timeUnix = timeUnix;
  }

  @JsonAnyGetter
  public Map<String, Object> getAdditionalProperties() {
    return this.additionalProperties;
  }

  @JsonAnySetter
  public void setAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
  }
}
