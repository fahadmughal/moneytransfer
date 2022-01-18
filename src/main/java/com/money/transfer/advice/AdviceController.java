package com.money.transfer.advice;

import com.money.transfer.dto.ErrorDto;
import com.money.transfer.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class AdviceController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InSufficientBalanceException.class)
    public ResponseEntity<Object> handleInsufficientBalanceException(
            InSufficientBalanceException ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorDto("Bal-01", ex.getMessage(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(DailyLimitExceededException.class)
    public ResponseEntity<Object> handleDailyLimitException(
            DailyLimitExceededException ex, WebRequest request) {

        return new ResponseEntity<>(new ErrorDto("Lim-01", ex.getMessage(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(AccountStatusException.class)
    public ResponseEntity<Object> handleAccountStatusException(
            AccountStatusException ex, WebRequest request) {

        return new ResponseEntity<>(new ErrorDto("State-01", ex.getMessage(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(PayLaterDateNotFoundException.class)
    public ResponseEntity<Object> handlePayLaterDateNotFoundException(
            PayLaterDateNotFoundException ex, WebRequest request) {

        return new ResponseEntity<>(new ErrorDto("Pld-01", ex.getMessage(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<Object> handleCustomerNotFoundException(
            CustomerNotFoundException ex, WebRequest request) {

        return new ResponseEntity<>(new ErrorDto("cst-01", ex.getMessage(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleOtherException(
            Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorDto("error-01", "Something went wrong", LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }
}
