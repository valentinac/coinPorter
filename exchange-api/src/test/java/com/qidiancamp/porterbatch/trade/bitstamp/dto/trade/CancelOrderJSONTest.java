package com.qidiancamp.porterbatch.trade.bitstamp.dto.trade;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test Transaction[] JSON parsing
 */
public class CancelOrderJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CancelOrderJSONTest.class.getResourceAsStream("/trade/example-cancel-order.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    Boolean result = mapper.readValue(is, Boolean.class);

    assertThat(result).isTrue();
  }
}
