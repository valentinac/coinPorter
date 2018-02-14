package com.qidiancamptech.porterbatch.trade.bitstamp.dto.marketdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test Transaction[] JSON parsing
 */
public class TradesJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = TradesJSONTest.class.getResourceAsStream("/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitstampTransaction[] transactions = mapper.readValue(is, BitstampTransaction[].class);

    // Verify that the example data was unmarshalled correctly
    assertThat(transactions[0].getDate()).isEqualTo(1357492264L);
    assertThat(transactions[0].getTid()).isEqualTo(122260);
    assertThat(transactions[0].getPrice()).isEqualTo(new BigDecimal("13.06"));
    assertThat(transactions[0].getAmount()).isEqualTo(new BigDecimal("28.75328052"));

  }
}
