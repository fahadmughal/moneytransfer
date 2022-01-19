package com.money.transfer.exception;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String accountNo, String customerId){
        super("Account no: " + accountNo + " does no belong to customer: " + customerId);
    }
}
