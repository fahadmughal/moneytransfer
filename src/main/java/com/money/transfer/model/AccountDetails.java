package com.money.transfer.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AccountDetails {
    @Id
    private Long id;
    private String accountTitle;
    private String accountNo;
    private String accountCurrency;
    private Date createdDate;
    private Date lastModifiedDate;
    private Double currentBalance;
    private Double availableBalance;
    private Double dailyLimit;
    @OneToOne(fetch = FetchType.EAGER, cascade= CascadeType.MERGE)
    @JoinColumn(name="accountType")
    private AccountType accountType;
    @OneToOne(fetch = FetchType.EAGER, cascade= CascadeType.MERGE)
    @JoinColumn(name="accountStatus")
    private AccountStatus accountStatus;
}
