package com.money.transfer.service;

import com.money.transfer.dto.TransactionDetailResponseDto;
import com.money.transfer.dto.TransactionRequestDto;
import com.money.transfer.enums.TransactionStatus;
import com.money.transfer.enums.TransactionType;
import com.money.transfer.model.TransactionDetails;
import com.money.transfer.repository.AccountDetailsRepository;
import com.money.transfer.repository.CustomerProfileRepository;
import com.money.transfer.repository.TransactionRepository;
import com.money.transfer.service.impl.TransactionServiceImpl;
import com.money.transfer.validator.TransactionValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceImplTest {
    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private TransactionValidator transactionValidator;
    @Mock
    private AccountService accountService;
    @Mock
    private CustomerProfileRepository customerProfileRepository;
    @Mock
    private AccountDetailsRepository accountDetailsRepository;

    private TransactionRequestDto transactionRequestDto;


    @Before
    public void init(){
        transactionRequestDto = new TransactionRequestDto();
        transactionRequestDto.setSourceAccountNo("123");
        transactionRequestDto.setDestinationAccountNo("456");
        transactionRequestDto.setTxnAmount(100.0);
        transactionRequestDto.setTxnType(TransactionType.PayNow.name());
        transactionRequestDto.setCustomerId("custId");
    }
    @Test
    public void testShouldPerformSuccessfulPayNowTxn(){
        doNothing().when(transactionValidator).customerDoesNotExistCheck(Mockito.any());
        doNothing().when(transactionValidator).doesAccountBelongsToCustomerCheck(Mockito.any());
        doNothing().when(transactionValidator).accountStatusCheck(Mockito.any());
        doNothing().when(transactionValidator).inSufficientBalanceCheck(Mockito.any());
        doNothing().when(transactionValidator).dailyLimitCheck(Mockito.any());

        doNothing().when(accountService).debitAccount(Mockito.any(), Mockito.any());
        doNothing().when(accountService).creditAccount(Mockito.any(), Mockito.any());
        TransactionDetails transactionDetails = new TransactionDetails();
        transactionDetails.setTxnRefNo("123");
        transactionDetails.setTxnAmount(100.0);
        transactionDetails.setSourceAccountNo("123");
        transactionDetails.setDestinationAccountNo("456");
        transactionDetails.setStatus(TransactionStatus.Success.name());
        transactionDetails.setTxnType(TransactionType.PayNow.name());
        when(transactionRepository.save(Mockito.any())).thenReturn(transactionDetails);

        TransactionDetailResponseDto transactionDetailResponseDto = transactionService.performTxn(transactionRequestDto);

        assertNotNull(transactionDetailResponseDto);
        assertNotNull(transactionDetailResponseDto.getTxnRefNo());
        assertEquals(TransactionStatus.Success.name(), transactionDetailResponseDto.getStatus());
        assertEquals(transactionRequestDto.getSourceAccountNo(), transactionDetailResponseDto.getSourceAccountNo());
        assertEquals(transactionRequestDto.getDestinationAccountNo(), transactionDetailResponseDto.getDestinationAccountNo());
        assertEquals(TransactionType.PayNow.name(), transactionDetailResponseDto.getTxnType());
    }
    @Test
    public void testShouldPerformSuccessfulPayLaterTxn(){

        transactionRequestDto.setTxnType(TransactionType.PayLater.name());
        doNothing().when(transactionValidator).customerDoesNotExistCheck(Mockito.any());
        doNothing().when(transactionValidator).doesAccountBelongsToCustomerCheck(Mockito.any());
        doNothing().when(transactionValidator).accountStatusCheck(Mockito.any());

        TransactionDetails transactionDetails = new TransactionDetails();
        transactionDetails.setTxnRefNo("123");
        transactionDetails.setTxnAmount(100.0);
        transactionDetails.setSourceAccountNo("123");
        transactionDetails.setDestinationAccountNo("456");
        transactionDetails.setStatus(TransactionStatus.Success.name());
        transactionDetails.setTxnType(TransactionType.PayLater.name());
        when(transactionRepository.save(Mockito.any())).thenReturn(transactionDetails);

        TransactionDetailResponseDto transactionDetailResponseDto = transactionService.performTxn(transactionRequestDto);

        assertNotNull(transactionDetailResponseDto);
        assertNotNull(transactionDetailResponseDto.getTxnRefNo());
        assertEquals(TransactionStatus.Success.name(), transactionDetailResponseDto.getStatus());
        assertEquals(transactionRequestDto.getSourceAccountNo(), transactionDetailResponseDto.getSourceAccountNo());
        assertEquals(transactionRequestDto.getDestinationAccountNo(), transactionDetailResponseDto.getDestinationAccountNo());
        assertEquals(TransactionType.PayLater.name(), transactionDetailResponseDto.getTxnType());
    }
    @Test
    public void testShouldPerformFailedPayNowTxn(){

        doNothing().when(transactionValidator).customerDoesNotExistCheck(Mockito.any());
        doNothing().when(transactionValidator).doesAccountBelongsToCustomerCheck(Mockito.any());
        doNothing().when(transactionValidator).accountStatusCheck(Mockito.any());
        doNothing().when(transactionValidator).inSufficientBalanceCheck(Mockito.any());
        doNothing().when(transactionValidator).dailyLimitCheck(Mockito.any());

        doThrow(NullPointerException.class).doNothing().when(accountService).debitAccount(Mockito.any(), Mockito.any());

        TransactionDetails transactionDetails = new TransactionDetails();
        transactionDetails.setTxnRefNo("123");
        transactionDetails.setStatus(TransactionStatus.Failed.name());
        transactionDetails.setTxnType(TransactionType.PayNow.name());
        when(transactionRepository.save(Mockito.any())).thenReturn(transactionDetails);

        TransactionDetailResponseDto transactionDetailResponseDto = transactionService.performTxn(transactionRequestDto);

        assertNotNull(transactionDetailResponseDto);
        assertNotNull(transactionDetailResponseDto.getTxnRefNo());
        assertEquals(TransactionStatus.Failed.name(), transactionDetailResponseDto.getStatus());
        assertEquals(TransactionType.PayNow.name(), transactionDetailResponseDto.getTxnType());
    }
    @Test
    public void testShouldFetchAllTxns(){
        TransactionDetails transactionDetails = new TransactionDetails();
        transactionDetails.setTxnRefNo("123");
        transactionDetails.setStatus(TransactionStatus.Failed.name());
        transactionDetails.setTxnType(TransactionType.PayNow.name());
        when(transactionRepository.findAll()).thenReturn(Collections.singletonList(transactionDetails));

        List<TransactionDetailResponseDto> responseDtoList = transactionService.fetchAllTransactions();

        assertFalse(responseDtoList.isEmpty());
    }
}
