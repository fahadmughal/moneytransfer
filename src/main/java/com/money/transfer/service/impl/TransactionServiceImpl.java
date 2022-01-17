package com.money.transfer.service.impl;

import com.money.transfer.dto.TransactionRequestDto;
import com.money.transfer.enums.TransactionStatus;
import com.money.transfer.enums.TransactionType;
import com.money.transfer.model.TransactionDetails;
import com.money.transfer.repository.TransactionRepository;
import com.money.transfer.service.AccountService;
import com.money.transfer.service.TransactionService;
import com.money.transfer.validator.TransactionValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionValidator transactionValidator;
    private final AccountService accountService;

    public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionValidator transactionValidator,
                                  AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.transactionValidator = transactionValidator;
        this.accountService = accountService;
    }


    /** TODO perform transaction
     ** TODO PESSIMISTIC READ implemented at repo method for handling concurrent txns
     ** TODO Pay later txn will be persisted with pending status in DB without debiting & crediting the accounts
     **/
    @Transactional
    @Override
    public TransactionDetails performTxn(TransactionRequestDto transactionRequestDto) {
        TransactionDetails transactionDetails = new TransactionDetails();
        BeanUtils.copyProperties(transactionRequestDto, transactionDetails);
        preTxnValidations(transactionDetails);
        try
        {
            // Using txn type enum instead of DB due to time constraint.
            if(transactionDetails.getTxnType().equals(TransactionType.PayNow.name()))
            {

                // Using txn status enum instead of DB due to time constraint.
                transactionDetails.setStatus(TransactionStatus.Success.name());
                transactionDetails.setTxnDate(new Date());
                //debiting source account
                accountService.debitAccount(transactionDetails.getSourceAccountNo(), transactionDetails.getTxnAomunt());
                //crediting destination account
                accountService.creditAccount(transactionDetails.getDestinationAccountNo(), transactionDetails.getTxnAomunt());
            }
            else {
                /*right now I am considering 2 statuses, that's why I used else, otherwise will be solution accordingly.
                Using txn status enum instead of DB due to time constraint.*/
                transactionDetails.setStatus(TransactionStatus.Pending.name());
            }
        }
        catch (Exception e){
            // Using txn status enum instead of DB due to time constraint.
            transactionDetails.setStatus(TransactionStatus.Failed.name());
            log.error(e.getMessage(), e);
        }
        return transactionRepository.save(transactionDetails);
    }

    /** TODO fetch all transactions*/
    @Override
    public List<TransactionDetails> fetchAllTransactions() {
        return (List<TransactionDetails>) transactionRepository.findAll();
    }

    /** TODO perform pre txn validations*/
    private void preTxnValidations(TransactionDetails transactionDetails){
        transactionValidator.accountStatusCheck(transactionDetails);
        transactionValidator.inSufficientBalanceCheck(transactionDetails);
        transactionValidator.dailyLimitCheck(transactionDetails);
        if (transactionDetails.getTxnType().equals(TransactionType.PayLater.name())){
            transactionValidator.payLaterDateCheck(transactionDetails);
        }
    }

}
