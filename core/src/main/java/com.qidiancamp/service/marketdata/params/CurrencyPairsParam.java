package com.qidiancamp.service.marketdata.params;

import java.util.Collection;
import com.qidiancamp.currency.CurrencyPair;

public interface CurrencyPairsParam extends Params {

  Collection<CurrencyPair> getCurrencyPairs();
}
