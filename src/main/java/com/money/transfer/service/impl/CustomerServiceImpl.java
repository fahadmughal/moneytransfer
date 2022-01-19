package com.money.transfer.service.impl;

import com.money.transfer.converter.AccountDetailsConverter;
import com.money.transfer.converter.CustomerProfileConverter;
import com.money.transfer.dto.AccountDetailsDto;
import com.money.transfer.dto.CustomerProfileDto;
import com.money.transfer.dto.CustomerTypeDto;
import com.money.transfer.model.CustomerProfile;
import com.money.transfer.repository.CustomerProfileRepository;
import com.money.transfer.service.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerProfileRepository customerProfileRepository;
    private final CustomerProfileConverter customerProfileConverter;

    public CustomerServiceImpl(CustomerProfileRepository customerProfileRepository, CustomerProfileConverter customerProfileConverter) {
        this.customerProfileRepository = customerProfileRepository;
        this.customerProfileConverter = customerProfileConverter;
    }

    @Override
    public List<CustomerProfileDto> findAllCustomer() {
        List<CustomerProfileDto> lstCustomerProfileDto = new ArrayList<>();
        customerProfileRepository.findAll().forEach(customerProfile -> {
            lstCustomerProfileDto.add(customerProfileConverter.convert(customerProfile));
        });
        return lstCustomerProfileDto;
    }

}
