package com.money.transfer.validator;

import com.money.transfer.enums.AccountStatusEnum;
import com.money.transfer.enums.TransactionType;
import com.money.transfer.exception.AccountStatusException;
import com.money.transfer.exception.DailyLimitExceededException;
import com.money.transfer.exception.InSufficientBalanceException;
import com.money.transfer.exception.PayLaterDateNotFoundException;
import com.money.transfer.model.AccountDetails;
import com.money.transfer.model.TransactionDetails;
import com.money.transfer.repository.AccountDetailsRepository;
import com.money.transfer.repository.TransactionRepository;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TransactionValidator {

    private final TransactionRepository transactionRepository;
    private final AccountDetailsRepository accountDetailsRepository;

    public TransactionValidator(TransactionRepository transactionRepository, AccountDetailsRepository accountDetailsRepository) {
        this.transactionRepository = transactionRepository;
        this.accountDetailsRepository = accountDetailsRepository;
    }

    /** TODO perform payLaterDateCheck*/
    public void payLaterDateCheck(TransactionDetails transactionDetails){
        if(transactionDetails.getTxnType().equals(TransactionType.PayLater.name()) && transactionDetails.getTxnDate() == null){
            throw new PayLaterDateNotFoundException();
        }
    }
    /** TODO perform accountStatusCheck whether its active or not*/
    public void accountStatusCheck(TransactionDetails transactionDetails){
        String accountStatus = accountDetailsRepository.findByAccountNo(
                transactionDetails.getSourceAccountNo()).getAccountStatus().getAccountStatus();
        if(!AccountStatusEnum.Active.name().equals(accountStatus)){
            throw new AccountStatusException(accountStatus);
        }
    }
    /** TODO perform dailyLimitCheck*/
    public void dailyLimitCheck(TransactionDetails transactionDetails){
        Double availedLimit = transactionRepository.sumOfDailyTxnAmountByAccountNo(transactionDetails.getSourceAccountNo(), new Date());
        availedLimit = (availedLimit == null ? 0.0 : availedLimit) + transactionDetails.getTxnAomunt();
        AccountDetails accountDetails = accountDetailsRepository.findByAccountNo(transactionDetails.getSourceAccountNo());

        if(availedLimit > accountDetails.getDailyLimit()){
            throw new DailyLimitExceededException();
        }
    }
    /** TODO perform inSufficient Balance Check*/
    public void inSufficientBalanceCheck(TransactionDetails transactionDetails){
        if(transactionDetails.getTxnAomunt() > accountDetailsRepository.findByAccountNo(transactionDetails.getSourceAccountNo()).getAvailableBalance()){
            throw new InSufficientBalanceException();
        }
    }
}
