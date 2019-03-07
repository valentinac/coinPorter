package com.qidiancamp.api.dragonex.service;

import com.qidiancamp.Exchange;
import com.qidiancamp.api.dragonex.dto.DragonResult;
import com.qidiancamp.api.dragonex.dto.DragonexException;
import com.qidiancamp.api.dragonex.dto.Token;
import com.qidiancamp.api.dragonex.dto.TokenStatus;
import com.qidiancamp.api.dragonex.dto.account.Balance;

import java.io.IOException;
import java.util.List;

public class DragonexAccountServiceRaw extends DragonexBaseService {

  public DragonexAccountServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public Token tokenNew() throws DragonexException, IOException {
    DragonResult<Token> tokenNew =
        exchange
            .dragonexAuthenticated()
            .tokenNew(utcNow(), exchange.signatureCreator(), ContentSHA1);
    return tokenNew.getResult();
  }

  public TokenStatus tokenStatus(String token) throws DragonexException, IOException {
    DragonResult<TokenStatus> tokenStatus =
        exchange
            .dragonexAuthenticated()
            .tokenStatus(utcNow(), token, exchange.signatureCreator(), ContentSHA1);
    return tokenStatus.getResult();
  }

  public List<Balance> userCoins(String token) throws DragonexException, IOException {
    DragonResult<List<Balance>> userCoins =
        exchange
            .dragonexAuthenticated()
            .userCoins(utcNow(), token, exchange.signatureCreator(), ContentSHA1);
    return userCoins.getResult();
  }
}
