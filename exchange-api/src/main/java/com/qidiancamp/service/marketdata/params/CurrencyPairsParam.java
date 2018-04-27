package com.qidiancamp.service.marketdata.params;

import com.qidiancamp.currency.CurrencyPair;
import java.util.Collection;

public interface CurrencyPairsParam extends Params {

  Collection<CurrencyPair> getCurrencyPairs();
}
