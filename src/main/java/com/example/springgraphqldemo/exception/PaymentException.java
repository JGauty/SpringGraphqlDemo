package com.example.springgraphqldemo.exception;

public class PaymentException extends RuntimeException {
    public PaymentException(String s) {
        super(s);
    }

    public PaymentException (String message, Throwable exception) {
        super(message, exception);
    }
}
