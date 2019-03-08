package com.qidiancamp.api.dragonex.service;


import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import com.qidiancamp.Exchange;
import com.qidiancamp.currency.Currency;
import com.qidiancamp.api.dragonex.dto.account.Balance;
import com.qidiancamp.dto.account.AccountInfo;
import com.qidiancamp.dto.account.Wallet;
import com.qidiancamp.service.account.AccountService;

public class DragonexAccountService extends DragonexAccountServiceRaw implements AccountService {

  public DragonexAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    List<Balance> userCoins = userCoins(exchange.getOrCreateToken().token);
    List<com.qidiancamp.dto.account.Balance> balances =
        userCoins.stream()
            .map(
                b ->
                    new com.qidiancamp.dto.account.Balance(
                        Currency.getInstance(b.code.toUpperCase()),
                        b.volume,
                        b.volume.subtract(b.frozen),
                        b.frozen))
            .collect(Collectors.toList());
    return new AccountInfo(new Wallet(balances));
  }
}
