package com.kirilarsov.mtls.client.util;

@FunctionalInterface
public interface CheckedFunction<R, T> {

  R apply(T t) throws Exception;
}
