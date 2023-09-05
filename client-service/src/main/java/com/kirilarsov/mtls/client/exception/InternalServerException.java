package com.kirilarsov.mtls.client.exception;

public class InternalServerException extends BaseException {

  public InternalServerException(int statusCode, String message) {
    super(statusCode, message);
  }

}
