package com.money.transfer.exception;

public class InSufficientBalanceException extends RuntimeException {
    public InSufficientBalanceException() {
        super("Insufficient balance is source account.");
    }
}