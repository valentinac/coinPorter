package com.qidiancamp.porterbatch.trade.bitstamp.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.qidiancamp.api.bitstamp.dto.account.WithdrawalRequest;
import org.junit.Test;

public class WithdrawalsJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = WithdrawalsJSONTest.class.getResourceAsStream("/account/withdrawals.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    List<WithdrawalRequest> withdrawals =
        mapper.readValue(
            is,
            mapper.getTypeFactory().constructCollectionType(List.class, WithdrawalRequest.class));

    assertThat(withdrawals.size()).isEqualTo(4);
    assertThat(withdrawals.get(0).getType()).isEqualTo(WithdrawalRequest.Type.bitcoin);
    assertThat(withdrawals.get(0).getStatus()).isEqualTo(WithdrawalRequest.Status.finished);

    assertThat(withdrawals.get(1).getType()).isEqualTo(WithdrawalRequest.Type.litecoin);
    assertThat(withdrawals.get(1).getStatus()).isEqualTo(WithdrawalRequest.Status.finished);

    assertThat(withdrawals.get(2).getType()).isEqualTo(WithdrawalRequest.Type.XRP);
    assertThat(withdrawals.get(2).getStatus()).isEqualTo(WithdrawalRequest.Status.unknown);
    assertThat(withdrawals.get(2).getStatusOriginal()).isEqualTo("10");

    assertThat(withdrawals.get(3).getType()).isEqualTo(WithdrawalRequest.Type.SEPA);
    assertThat(withdrawals.get(3).getStatus()).isEqualTo(WithdrawalRequest.Status.unknown);
    assertThat(withdrawals.get(3).getStatusOriginal()).isEqualTo("8");
  }
}
