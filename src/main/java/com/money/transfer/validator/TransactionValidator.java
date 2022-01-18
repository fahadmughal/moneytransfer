package com.money.transfer.validator;

import com.money.transfer.dto.TransactionRequestDto;
import com.money.transfer.enums.AccountStatusEnum;
import com.money.transfer.enums.TransactionType;
import com.money.transfer.exception.AccountStatusException;
import com.money.transfer.exception.DailyLimitExceededException;
import com.money.transfer.exception.InSufficientBalanceException;
import com.money.transfer.exception.PayLaterDateNotFoundException;
import com.money.transfer.model.AccountDetails;
import com.money.transfer.repository.AccountDetailsRepository;
import com.money.transfer.repository.CustomerProfileRepository;
import com.money.transfer.repository.TransactionRepository;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TransactionValidator {

    private final TransactionRepository transactionRepository;
    private final AccountDetailsRepository accountDetailsRepository;
    private final CustomerProfileRepository customerProfileRepository;

    public TransactionValidator(TransactionRepository transactionRepository, AccountDetailsRepository accountDetailsRepository,
                                CustomerProfileRepository customerProfileRepository) {
        this.transactionRepository = transactionRepository;
        this.accountDetailsRepository = accountDetailsRepository;
        this.customerProfileRepository = customerProfileRepository;
    }

    /** TODO perform customerDoesNotExistCheck*/
    public void customerDoesNotExistCheck(TransactionRequestDto transactionDetails){
        if(!customerProfileRepository.existsCustomerProfileByCustomerId(transactionDetails.getCustomerId())){
            throw new PayLaterDateNotFoundException();
        }
    }
    /** TODO perform payLaterDateCheck*/
    public void payLaterDateCheck(TransactionRequestDto transactionDetails){
        if(transactionDetails.getTxnType().equals(TransactionType.PayLater.name()) && transactionDetails.getTxnDate() == null){
            throw new PayLaterDateNotFoundException();
        }
    }
    /** TODO perform accountStatusCheck whether its active or not*/
    public void accountStatusCheck(TransactionRequestDto transactionDetails){
        String accountStatus = accountDetailsRepository.findByAccountNo(
                transactionDetails.getSourceAccountNo()).getAccountStatus().getAccountStatus();
        if(!AccountStatusEnum.Active.name().equals(accountStatus)){
            throw new AccountStatusException(accountStatus);
        }
    }
    /** TODO perform dailyLimitCheck*/
    public void dailyLimitCheck(TransactionRequestDto transactionDetails){
        Double availedLimit = transactionRepository.sumOfDailyTxnAmountByAccountNo(transactionDetails.getSourceAccountNo(), new Date());
        availedLimit = (availedLimit == null ? 0.0 : availedLimit) + transactionDetails.getTxnAmount();
        AccountDetails accountDetails = accountDetailsRepository.findByAccountNo(transactionDetails.getSourceAccountNo());

        if(availedLimit > accountDetails.getDailyLimit()){
            throw new DailyLimitExceededException();
        }
    }
    /** TODO perform inSufficient Balance Check*/
    public void inSufficientBalanceCheck(TransactionRequestDto transactionDetails){
        if(transactionDetails.getTxnAmount() > accountDetailsRepository.findByAccountNo(transactionDetails.getSourceAccountNo()).getAvailableBalance()){
            throw new InSufficientBalanceException();
        }
    }
}
