package com.money.transfer.exception;

public class PayLaterDateNotFoundException extends RuntimeException {
    public PayLaterDateNotFoundException() {
        super("Pay Later date not found.");
    }
}
