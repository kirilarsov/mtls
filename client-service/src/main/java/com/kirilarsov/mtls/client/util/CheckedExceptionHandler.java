package com.kirilarsov.mtls.client.util;

import java.io.IOException;
import java.io.UncheckedIOException;

public class CheckedExceptionHandler {

  private CheckedExceptionHandler() {}

  public static <R, T1, T2> R handleCheckedException(
      CheckedBiFunction<R, T1, T2> function, T1 param1, T2 param2) {
    try {
      return function.apply(param1, param2);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException(e);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static <R, T> R handleCheckedException(CheckedFunction<R, T> function, T param) {
    try {
      return function.apply(param);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException(e);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
