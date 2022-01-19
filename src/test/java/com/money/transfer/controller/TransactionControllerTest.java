package com.money.transfer.controller;

import com.money.transfer.dto.TransactionDetailResponseDto;
import com.money.transfer.dto.TransactionRequestDto;
import com.money.transfer.service.TransactionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionControllerTest {
    @InjectMocks
    private TransactionController transactionController;
    @Mock
    private TransactionService transactionService;

    private TransactionDetailResponseDto transactionDetailResponseDto;

    @Before
    public void init(){
        transactionDetailResponseDto = new TransactionDetailResponseDto();
        transactionDetailResponseDto.setSourceAccountNo("123");
        transactionDetailResponseDto.setDestinationAccountNo("456");
        transactionDetailResponseDto.setTxnAmount(100.0);
    }

    @Test
    public void testShouldPostSuccessfulTxn(){
        TransactionRequestDto transactionRequestDto = new TransactionRequestDto();
        transactionRequestDto.setSourceAccountNo("123");
        transactionRequestDto.setDestinationAccountNo("456");
        transactionRequestDto.setTxnAmount(100.0);

        when(transactionService.performTxn(Mockito.any())).thenReturn(transactionDetailResponseDto);
        ResponseEntity<TransactionDetailResponseDto> responseEntity = transactionController.performTxn(new TransactionRequestDto());

        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(transactionRequestDto.getSourceAccountNo(), Objects.requireNonNull(responseEntity.getBody()).getSourceAccountNo());
        assertEquals(transactionRequestDto.getDestinationAccountNo(), Objects.requireNonNull(responseEntity.getBody()).getDestinationAccountNo());
        assertEquals(transactionRequestDto.getTxnAmount(), Objects.requireNonNull(responseEntity.getBody()).getTxnAmount());
    }
    @Test
    public void testShouldBeAbleToGetListOfTxns(){
        TransactionDetailResponseDto transactionDetailResponseDto = new TransactionDetailResponseDto();
        transactionDetailResponseDto.setSourceAccountNo("123");
        transactionDetailResponseDto.setDestinationAccountNo("456");
        transactionDetailResponseDto.setTxnAmount(100.0);
        when(transactionService.fetchAllTransactions()).thenReturn(Collections.singletonList(transactionDetailResponseDto));

        ResponseEntity<List<TransactionDetailResponseDto>> listResponseEntity = transactionController.fetchAllTransactions();
        TransactionDetailResponseDto result = Objects.requireNonNull(listResponseEntity.getBody()).get(0);

        assertNotNull(listResponseEntity);
        assertEquals(listResponseEntity.getStatusCode(), HttpStatus.OK);
        assertFalse(Objects.requireNonNull(listResponseEntity.getBody()).isEmpty());
        assertEquals(transactionDetailResponseDto.getSourceAccountNo(), result.getSourceAccountNo());
        assertEquals(transactionDetailResponseDto.getDestinationAccountNo(), result.getDestinationAccountNo());
        assertEquals(transactionDetailResponseDto.getTxnAmount(), result.getTxnAmount());
    }

    @Test
    public void testShouldBeAbleToGetEmptyList(){
        when(transactionService.fetchAllTransactions()).thenReturn(new ArrayList<>());

        ResponseEntity<List<TransactionDetailResponseDto>> listResponseEntity = transactionController.fetchAllTransactions();

        assertNotNull(listResponseEntity);
        assertEquals(listResponseEntity.getStatusCode(), HttpStatus.NO_CONTENT);
        assertTrue(Objects.requireNonNull(listResponseEntity.getBody()).isEmpty());
    }
}
