package com.qidiancamptech.api.gate.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.knowm.xchange.gateio.dto.GateioOrderType.BTEROrderTypeDeserializer;

import java.io.IOException;

@JsonDeserialize(using = BTEROrderTypeDeserializer.class)
public enum GateioOrderType {

  BUY, SELL;

  static class BTEROrderTypeDeserializer extends JsonDeserializer<GateioOrderType> {

    @Override
    public GateioOrderType deserialize(JsonParser jsonParser, final DeserializationContext ctxt) throws IOException, JsonProcessingException {

      final ObjectCodec oc = jsonParser.getCodec();
      final JsonNode node = oc.readTree(jsonParser);
      final String orderType = node.asText();
      return GateioOrderType.valueOf(orderType.toUpperCase());
    }
  }
}
