package com.money.transfer.service;

import com.money.transfer.dto.AccountDetailsDto;

import java.util.List;

public interface AccountService {
    List<AccountDetailsDto> fetchAllAccounts();
    void debitAccount(String accountNo, Double amount);
    void creditAccount(String accountNo, Double amount);
}
