package com.money.transfer.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AccountType {
    @Id
    private Long id;
    private String accountTypeName;
    private String accountTypeDesc;
}
