package com.money.transfer.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountTypeDto {
    private Long id;
    private String accountTypeName;
    private String accountTypeDesc;
}
