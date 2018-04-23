package com.qidiancamp.api.bitstamp.dto;

public class BitstampBaseResponse {

  private final String error;

  protected BitstampBaseResponse(String error) {

    this.error = error;
  }

  public String getError() {

    return error;
  }
}
