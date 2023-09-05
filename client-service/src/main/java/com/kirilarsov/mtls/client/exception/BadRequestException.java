package com.kirilarsov.mtls.client.exception;

public class BadRequestException extends BaseException {

  public BadRequestException(int statusCode, String message) {
    super(statusCode, message);
  }
}
