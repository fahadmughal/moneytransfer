package com.money.transfer.dto;

import com.money.transfer.model.AccountStatus;
import com.money.transfer.model.AccountType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.Date;

@Getter
@Setter
public class AccountDetailsDto {
    private String accountTitle;
    private String accountNo;
    private String accountCurrency;
    private Date createdDate;
    private Date lastModifiedDate;
    private Double currentBalance;
    private Double availableBalance;
    private Double dailyLimit;
    private AccountTypeDto accountType;
    private AccountStatusDto accountStatus;
}
