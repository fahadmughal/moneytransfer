package com.money.transfer.service;

import com.money.transfer.dto.CustomerProfileDto;
import com.money.transfer.model.CustomerProfile;

import java.util.List;

public interface CustomerService {
    List<CustomerProfileDto> findAllCustomer();
}
