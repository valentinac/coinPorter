package com.qidiancamp.api.binance_hs.impl;

import com.qidiancamp.api.binance_hs.BinanceApiCallback;
import com.qidiancamp.api.binance_hs.BinanceApiError;
import com.qidiancamp.api.binance_hs.exception.BinanceApiException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;

import static com.qidiancamp.api.binance_hs.impl.BinanceApiServiceGenerator.getBinanceApiError;

/**
 * An adapter/wrapper which transforms a Callback from Retrofit into a BinanceApiCallback which is exposed to the client.
 */
public class BinanceApiCallbackAdapter<T> implements Callback<T> {

  private final BinanceApiCallback<T> callback;

  public BinanceApiCallbackAdapter(BinanceApiCallback<T> callback) {
    this.callback = callback;
  }

  public void onResponse(Call<T> call, Response<T> response) {
    if (response.isSuccessful()) {
      callback.onResponse(response.body());
    } else {
      if (response.code() == 504) {
        // HTTP 504 return code is used when the API successfully sent the message but not get a response within the timeout period.
        // It is important to NOT treat this as a failure; the execution status is UNKNOWN and could have been a success.
        return;
      }
      try {
        BinanceApiError apiError = getBinanceApiError(response);
        throw new BinanceApiException(apiError);
      } catch (IOException e) {
        throw new BinanceApiException(e);
      }
    }
  }

  @Override
  public void onFailure(Call<T> call, Throwable throwable) {
    throw new BinanceApiException(throwable);
  }
}
