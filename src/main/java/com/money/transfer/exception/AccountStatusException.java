package com.money.transfer.exception;

public class AccountStatusException extends RuntimeException {
    public AccountStatusException(String accountStatus){
        super("Account is in " + accountStatus + " state.");
    }
}
