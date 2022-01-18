package com.money.transfer.exception;

public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException(String customerId){
        super("Customer not found, customer Id: " + customerId);
    }
}
