package com.qidiancamp.api.dragonex.service;


import com.qidiancamp.Exchange;
import com.qidiancamp.api.dragonex.dto.account.Balance;
import com.qidiancamp.currency.Currency;
import com.qidiancamp.dto.account.AccountInfo;
import com.qidiancamp.dto.account.Wallet;
import com.qidiancamp.service.account.AccountService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class DragonexAccountService extends DragonexAccountServiceRaw implements AccountService {

  public DragonexAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    List<Balance> userCoins = userCoins(exchange.getOrCreateToken().token);
    List<Balance> balances =
        userCoins.stream()
            .map(
                b ->
                    new Balance(
                        Currency.getInstance(b.code.toUpperCase()),
                        b.volume,
                        b.volume.subtract(b.frozen),
                        b.frozen))
            .collect(Collectors.toList());
    return new AccountInfo(new Wallet(balances));
  }
}
