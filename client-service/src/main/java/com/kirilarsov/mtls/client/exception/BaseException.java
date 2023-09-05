package com.kirilarsov.mtls.client.exception;

public class BaseException extends RuntimeException {
  private final int statusCode;
  private final String message;

  public BaseException(int statusCode, String message) {
    this.statusCode = statusCode;
    this.message = message;
  }

  public int getStatusCode() {
    return statusCode;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
