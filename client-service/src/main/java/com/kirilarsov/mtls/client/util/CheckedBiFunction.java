package com.kirilarsov.mtls.client.util;

@FunctionalInterface
public interface CheckedBiFunction<R, T1, T2> {

  R apply(T1 t1, T2 t2) throws Exception;
}
