package com.money.transfer.service;

import com.money.transfer.model.AccountDetails;

import java.util.List;

public interface AccountService {
    List<AccountDetails> fetchAllAccounts();
    AccountDetails fetchAccountById(Long id);
    void debitAccount(String accountNo, Double amount) throws InterruptedException;
    void creditAccount(String accountNo, Double amount);
}
