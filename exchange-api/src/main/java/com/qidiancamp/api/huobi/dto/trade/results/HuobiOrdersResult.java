package com.qidiancamp.api.huobi.dto.trade.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.qidiancamp.api.huobi.dto.HuobiResult;
import com.qidiancamp.api.huobi.dto.trade.HuobiOrder;

public class HuobiOrdersResult extends HuobiResult<HuobiOrder[]> {

  public HuobiOrdersResult(
      @JsonProperty("status") String status,
      @JsonProperty("data") HuobiOrder[] result,
      @JsonProperty("err-code") String errCode,
      @JsonProperty("err-msg") String errMsg) {
    super(status, errCode, errMsg, result);
  }
}
