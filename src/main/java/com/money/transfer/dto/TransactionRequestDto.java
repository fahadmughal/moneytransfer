package com.money.transfer.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class TransactionRequestDto {
    @NotNull
    private String sourceAccountNo;
    @NotNull
    private String destinationAccountNo;
    @NotNull
    private String txnCurrency;
    @NotNull
    private Double txnAomunt;
    @Temporal(TemporalType.DATE)
    private Date txnDate;
    @NotNull
    private String txnType;
}
