package com.money.transfer.validator;

import com.money.transfer.dto.TransactionRequestDto;
import com.money.transfer.enums.TransactionType;
import com.money.transfer.exception.*;
import com.money.transfer.model.AccountDetails;
import com.money.transfer.model.AccountStatus;
import com.money.transfer.model.CustomerProfile;
import com.money.transfer.repository.AccountDetailsRepository;
import com.money.transfer.repository.CustomerProfileRepository;
import com.money.transfer.repository.TransactionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionValidatorTest {
    @InjectMocks
    private TransactionValidator transactionValidator;

    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private AccountDetailsRepository accountDetailsRepository;
    @Mock
    private CustomerProfileRepository customerProfileRepository;

    @Test(expected = AccountNotFoundException.class)
    public void testShouldThrowAccountNotFoundException(){
        when(customerProfileRepository.findByCustomerId(Mockito.any())).thenReturn(new CustomerProfile());
        when(accountDetailsRepository.existAccountForCustomerOrNot(Mockito.any(), Mockito.any())).thenReturn(false);

        transactionValidator.doesAccountBelongsToCustomerCheck(new TransactionRequestDto());
    }
    @Test
    public void testShouldNotThrowAccountNotFoundException(){
        when(customerProfileRepository.findByCustomerId(Mockito.any())).thenReturn(new CustomerProfile());
        when(accountDetailsRepository.existAccountForCustomerOrNot(Mockito.any(), Mockito.any())).thenReturn(true);

        transactionValidator.doesAccountBelongsToCustomerCheck(new TransactionRequestDto());
    }

    @Test(expected = CustomerNotFoundException.class)
    public void testShouldThrowCustomerNotFoundException(){
        when(customerProfileRepository.existsByCustomerId(Mockito.any())).thenReturn(false);
        transactionValidator.customerDoesNotExistCheck(new TransactionRequestDto());
    }
    @Test
    public void testShouldNotThrowCustomerNotFoundException(){
        when(customerProfileRepository.existsByCustomerId(Mockito.any())).thenReturn(true);
        transactionValidator.customerDoesNotExistCheck(new TransactionRequestDto());
    }
    @Test(expected = PayLaterDateNotFoundException.class)
    public void testShouldThrowPayLaterDateNotFoundException(){
        when(customerProfileRepository.existsByCustomerId(Mockito.any())).thenReturn(false);
        TransactionRequestDto transactionRequestDto = new TransactionRequestDto();
        transactionRequestDto.setTxnType(TransactionType.PayLater.name());
        transactionValidator.payLaterDateCheck(transactionRequestDto);
    }
    @Test
    public void testShouldTNothrowPayLaterDateNotFoundException(){
        when(customerProfileRepository.existsByCustomerId(Mockito.any())).thenReturn(true);
        TransactionRequestDto transactionRequestDto = new TransactionRequestDto();
        transactionRequestDto.setTxnType(TransactionType.PayLater.name());
        transactionRequestDto.setTxnDate(new Date());
        transactionValidator.payLaterDateCheck(transactionRequestDto);
    }

    @Test(expected = AccountStatusException.class)
    public void testShouldThrowAccountStatusException(){
        AccountStatus accountStatus = new AccountStatus();
        accountStatus.setAccountStatus("InActive");
        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setAccountStatus(accountStatus);
        when(accountDetailsRepository.findByAccountNo(Mockito.any())).thenReturn(accountDetails);

        transactionValidator.accountStatusCheck(new TransactionRequestDto());
    }
    @Test
    public void testShouldNotThrowAccountStatusException(){
        AccountStatus accountStatus = new AccountStatus();
        accountStatus.setAccountStatus("Active");
        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setAccountStatus(accountStatus);
        when(accountDetailsRepository.findByAccountNo(Mockito.any())).thenReturn(accountDetails);

        transactionValidator.accountStatusCheck(new TransactionRequestDto());
    }

    @Test(expected = DailyLimitExceededException.class)
    public void testShouldThrowDailyLimitExceededException(){
        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setDailyLimit(500.0);
        when(transactionRepository.sumOfDailyTxnAmountByAccountNo(Mockito.any(), Mockito.any())).thenReturn(10.0);
        when(accountDetailsRepository.findByAccountNo(Mockito.any())).thenReturn(accountDetails);

        TransactionRequestDto transactionRequestDto = new TransactionRequestDto();
        transactionRequestDto.setTxnAmount(10000.0);
        transactionValidator.dailyLimitCheck(transactionRequestDto);
    }
    @Test
    public void testShouldNotThrowDailyLimitExceededException(){
        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setDailyLimit(500.0);
        when(transactionRepository.sumOfDailyTxnAmountByAccountNo(Mockito.any(), Mockito.any())).thenReturn(10.0);
        when(accountDetailsRepository.findByAccountNo(Mockito.any())).thenReturn(accountDetails);

        TransactionRequestDto transactionRequestDto = new TransactionRequestDto();
        transactionRequestDto.setTxnAmount(100.0);
        transactionValidator.dailyLimitCheck(transactionRequestDto);
    }

    @Test(expected = InSufficientBalanceException.class)
    public void testShouldThrowInSufficientBalanceException(){
        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setDailyLimit(500.0);
        accountDetails.setAvailableBalance(10.0);
        when(accountDetailsRepository.findByAccountNo(Mockito.any())).thenReturn(accountDetails);

        TransactionRequestDto transactionRequestDto = new TransactionRequestDto();
        transactionRequestDto.setTxnAmount(10000.0);
        transactionValidator.inSufficientBalanceCheck(transactionRequestDto);
    }
    @Test
    public void testShouldNotThrowInSufficientBalanceException(){
        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setDailyLimit(500.0);
        accountDetails.setAvailableBalance(10.0);
        when(accountDetailsRepository.findByAccountNo(Mockito.any())).thenReturn(accountDetails);

        TransactionRequestDto transactionRequestDto = new TransactionRequestDto();
        transactionRequestDto.setTxnAmount(2.0);
        transactionValidator.inSufficientBalanceCheck(transactionRequestDto);
    }

}
