package com.money.transfer.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
public class TransactionDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
