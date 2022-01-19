package com.money.transfer.dto;

import com.money.transfer.model.AccountDetails;
import com.money.transfer.model.CustomerType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CustomerProfileDto {
    private String customerId;
    private String firstName;
    private String lastName;
    private String primaryAddress;
    private String secondaryAddress;
    private String primaryMobile;
    private String secondaryMobile;
    private CustomerTypeDto customerType;
    private List<AccountDetailsDto> lstOfAccounts = new ArrayList<>();
}
