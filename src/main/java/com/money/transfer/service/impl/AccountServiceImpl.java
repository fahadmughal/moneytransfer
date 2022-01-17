package com.money.transfer.service.impl;

import com.money.transfer.model.AccountDetails;
import com.money.transfer.repository.AccountDetailsRepository;
import com.money.transfer.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountDetailsRepository accountDetailsRepository;

    public AccountServiceImpl(AccountDetailsRepository accountDetailsRepository) {
        this.accountDetailsRepository = accountDetailsRepository;
    }

    /** TODO fetch all accounts*/
    @Override
    public List<AccountDetails> fetchAllAccounts() {
        return accountDetailsRepository.findAll();
    }

    /** TODO fetch account by id*/
    @Override
    public AccountDetails fetchAccountById(Long id) {
        Optional optional = accountDetailsRepository.findById(id);
        return optional.isPresent() ? (AccountDetails) optional.get() : null;
    }

    /** TODO debit account
     ** TODO PESSIMISTIC READ implemented at repo method for handling concurrent txns
     **/
    @Override
    public void debitAccount(String accountNo, Double amount) throws InterruptedException {
        AccountDetails accountDetails = accountDetailsRepository.findByAccountNo(accountNo);
        accountDetails.setAvailableBalance(accountDetails.getCurrentBalance() - amount);
        accountDetails.setCurrentBalance(accountDetails.getCurrentBalance() - amount);

        accountDetailsRepository.save(accountDetails);
    }

    /** TODO credit account*/
    @Override
    public void creditAccount(String accountNo, Double amount) {
        AccountDetails accountDetails = accountDetailsRepository.findByAccountNo(accountNo);
        accountDetails.setAvailableBalance(accountDetails.getCurrentBalance() + amount);
        accountDetails.setCurrentBalance(accountDetails.getCurrentBalance() + amount);

        accountDetailsRepository.save(accountDetails);
    }

}
