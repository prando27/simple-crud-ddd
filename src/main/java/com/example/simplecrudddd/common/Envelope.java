package com.example.simplecrudddd.common;

import lombok.Getter;

@Getter
public class Envelope<T> {

    private final T result;

    private final String errorMessage;

    private Envelope(T result, String errorMessage) {
        this.result = result;
        this.errorMessage = errorMessage;
    }

    public static <T> Envelope<T> ok(T result) {
        return new Envelope<>(result, null);
    }

    public static <T> Envelope<T> error(String errorMessage) {
        return new Envelope<>(null, errorMessage);
    }
}
