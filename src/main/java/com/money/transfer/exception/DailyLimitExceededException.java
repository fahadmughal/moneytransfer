package com.money.transfer.exception;

public class DailyLimitExceededException extends RuntimeException {
    public DailyLimitExceededException() {
        super("Daily limit exceeded.");
    }
}
