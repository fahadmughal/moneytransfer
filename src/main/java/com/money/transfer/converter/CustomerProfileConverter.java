package com.money.transfer.converter;

import com.money.transfer.dto.CustomerProfileDto;
import com.money.transfer.dto.CustomerTypeDto;
import com.money.transfer.model.CustomerProfile;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CustomerProfileConverter implements Converter<CustomerProfile, CustomerProfileDto> {
    private final AccountDetailsConverter accountDetailsConverter;

    public CustomerProfileConverter(AccountDetailsConverter accountDetailsConverter) {
        this.accountDetailsConverter = accountDetailsConverter;
    }

    @Override
    public CustomerProfileDto convert(CustomerProfile customerProfile) {
        CustomerProfileDto customerProfileDto = new CustomerProfileDto();
        BeanUtils.copyProperties(customerProfile, customerProfileDto);
        CustomerTypeDto customerTypeDto = new CustomerTypeDto();
        BeanUtils.copyProperties(customerProfile.getCustomerType(), customerTypeDto);

        customerProfileDto.setCustomerType(customerTypeDto);
        customerProfileDto.setLstOfAccounts(accountDetailsConverter.convert(customerProfile.getLstOfAccounts()));
        return customerProfileDto;
    }
}
