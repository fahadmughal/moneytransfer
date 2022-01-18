package com.money.transfer.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Getter
@Setter
public class TransactionDetailResponseDto {
    private String txnRefNo;
    private String sourceAccountNo;
    private String destinationAccountNo;
    private String txnCurrency;
    private Double txnAmount;
    @Temporal(TemporalType.DATE)
    private Date txnDate;
    private String txnType;
    private String status;
}
