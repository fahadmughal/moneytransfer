package com.money.transfer.service;

import com.money.transfer.dto.TransactionDetailResponseDto;
import com.money.transfer.dto.TransactionRequestDto;
import com.money.transfer.model.TransactionDetails;

import java.util.List;

public interface TransactionService {
    TransactionDetailResponseDto performTxn(TransactionRequestDto transactionRequestDto);
    List<TransactionDetailResponseDto> fetchAllTransactions();
}
