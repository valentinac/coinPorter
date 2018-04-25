package com.qidiancamp.api.gate.dto.trade;

import com.fasterxml.jackson.annotation.*;
import com.qidiancamp.api.GateioBaseResponse;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({"result", "trades", "msg"})
public class GateioTradeHistoryReturn extends GateioBaseResponse {

  @JsonProperty("result")
  private Boolean result;
  @JsonProperty("trades")
  private List<GateioTrade> trades = new ArrayList<GateioTrade>();
  @JsonProperty("msg")
  private String msg;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  protected GateioTradeHistoryReturn(@JsonProperty("result") boolean result, @JsonProperty("trades") List<GateioTrade> trades,
      @JsonProperty("msg") String message) {

    super(result, message);
    this.trades = trades;
  }

  @JsonProperty("result")
  public Boolean getResult() {

    return result;
  }

  @JsonProperty("result")
  public void setResult(Boolean result) {

    this.result = result;
  }

  @JsonProperty("trades")
  public List<GateioTrade> getTrades() {

    return trades;
  }

  @JsonProperty("trades")
  public void setTrades(List<GateioTrade> trades) {

    this.trades = trades;
  }

  @JsonProperty("msg")
  public String getMsg() {

    return msg;
  }

  @JsonProperty("msg")
  public void setMsg(String msg) {

    this.msg = msg;
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
