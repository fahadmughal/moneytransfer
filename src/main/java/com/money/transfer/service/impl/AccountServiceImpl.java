package com.money.transfer.service.impl;

import com.money.transfer.converter.AccountDetailsConverter;
import com.money.transfer.dto.AccountDetailsDto;
import com.money.transfer.dto.AccountStatusDto;
import com.money.transfer.dto.AccountTypeDto;
import com.money.transfer.model.AccountDetails;
import com.money.transfer.model.AccountStatus;
import com.money.transfer.repository.AccountDetailsRepository;
import com.money.transfer.service.AccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountDetailsRepository accountDetailsRepository;
    private final AccountDetailsConverter accountDetailsConverter;

    public AccountServiceImpl(AccountDetailsRepository accountDetailsRepository, AccountDetailsConverter accountDetailsConverter) {
        this.accountDetailsRepository = accountDetailsRepository;
        this.accountDetailsConverter = accountDetailsConverter;
    }

    /** TODO fetch all accounts */
    @Override
    public List<AccountDetailsDto> fetchAllAccounts() {
        return accountDetailsConverter.convert(accountDetailsRepository.findAll());
    }

    /** TODO debit account
     ** TODO PESSIMISTIC READ implemented at repo method for handling concurrent txns
     **/
    @Override
    public void debitAccount(String accountNo, Double amount) {
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

//    private List<AccountDetailsDto> mapAccountDetailsToAccountDetailsDto(List<AccountDetails> lstAccountDetails){
//        List<AccountDetailsDto> lstAccountDetailsDto = new ArrayList<>();
//        lstAccountDetails.forEach(accountDetails -> {
//            AccountDetailsDto accountDetailsDto = new AccountDetailsDto();
//            BeanUtils.copyProperties(accountDetails, accountDetailsDto);
//            AccountStatusDto accountStatusDto = new AccountStatusDto();
//            BeanUtils.copyProperties(accountDetails.getAccountStatus(), accountStatusDto);
//            AccountTypeDto accountTypeDto = new AccountTypeDto();
//            BeanUtils.copyProperties(accountDetails.getAccountType(), accountTypeDto);
//
//            accountDetailsDto.setAccountType(accountTypeDto);
//            accountDetailsDto.setAccountStatus(accountStatusDto);
//            lstAccountDetailsDto.add(accountDetailsDto);
//        });
//        return lstAccountDetailsDto;
//    }

}
