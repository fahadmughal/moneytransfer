package com.money.transfer.service.impl;

import com.money.transfer.dto.TransactionDetailResponseDto;
import com.money.transfer.dto.TransactionRequestDto;
import com.money.transfer.enums.TransactionStatus;
import com.money.transfer.enums.TransactionType;
import com.money.transfer.model.TransactionDetails;
import com.money.transfer.repository.TransactionRepository;
import com.money.transfer.service.AccountService;
import com.money.transfer.service.TransactionService;
import com.money.transfer.validator.TransactionValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    public TransactionDetailResponseDto performTxn(TransactionRequestDto transactionRequestDto) {
        TransactionDetails transactionDetails = new TransactionDetails();
        preTxnValidations(transactionRequestDto);
        // copying from request dto to entity
        BeanUtils.copyProperties(transactionRequestDto, transactionDetails);
        transactionDetails.setTxnRefNo(RandomStringUtils.randomAlphanumeric(8));
        try
        {
            // Using txn type enum instead of DB due to time constraint.
            if(transactionDetails.getTxnType().equals(TransactionType.PayNow.name()))
            {
                // Using txn status enum instead of DB due to time constraint.
                transactionDetails.setStatus(TransactionStatus.Success.name());
                transactionDetails.setTxnDate(new Date());
                //debiting source account
                accountService.debitAccount(transactionDetails.getSourceAccountNo(), transactionDetails.getTxnAmount());
                //crediting destination account
                accountService.creditAccount(transactionDetails.getDestinationAccountNo(), transactionDetails.getTxnAmount());
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
            // embedded the txn ref no for tracking txn in error logs
            log.error("Txn ref no: " + transactionDetails.getTxnRefNo() , e);
        }
        TransactionDetails details = transactionRepository.save(transactionDetails);
        TransactionDetailResponseDto transactionDetailResponseDto = new TransactionDetailResponseDto();
        // copying from entity to response dto
        BeanUtils.copyProperties(details, transactionDetailResponseDto);
        return transactionDetailResponseDto;
    }

    /** TODO fetch all transactions*/
    @Override
    public List<TransactionDetailResponseDto> fetchAllTransactions() {
        List<TransactionDetailResponseDto> lstTransactionDetailResponseDto = new ArrayList<>();
        transactionRepository.findAll().forEach(
                (transactionDetails)-> {
                    TransactionDetailResponseDto transactionDetailResponseDto = new TransactionDetailResponseDto();
                    BeanUtils.copyProperties(transactionDetails, transactionDetailResponseDto);
                    lstTransactionDetailResponseDto.add(transactionDetailResponseDto);
                }
        );
        return lstTransactionDetailResponseDto;
    }

    /** TODO perform pre txn validations*/
    private void preTxnValidations(TransactionRequestDto transactionDetails){
        transactionValidator.customerDoesNotExistCheck(transactionDetails);
        transactionValidator.doesAccountBelongsToCustomerCheck(transactionDetails);
        transactionValidator.accountStatusCheck(transactionDetails);
        if (transactionDetails.getTxnType().equals(TransactionType.PayLater.name())){
            transactionValidator.payLaterDateCheck(transactionDetails);
        }
        else {
            transactionValidator.inSufficientBalanceCheck(transactionDetails);
            transactionValidator.dailyLimitCheck(transactionDetails);
        }
    }

}
