package com.money.transfer.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CustomerProfile {
    @Id
    private Long id;
    private String customerId;
    private String firstName;
    private String lastName;
    private String primaryAddress;
    private String secondaryAddress;
    private String primaryMobile;
    private String secondaryMobile;
    @OneToOne(fetch = FetchType.EAGER, cascade= CascadeType.MERGE)
    @JoinColumn(name="customerType")
    private CustomerType customerType;
    @OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name="customerProfileId")
    private List<AccountDetails> lstOfAccounts = new ArrayList<>();
}
