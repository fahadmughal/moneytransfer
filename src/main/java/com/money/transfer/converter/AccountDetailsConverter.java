package com.money.transfer.converter;

import com.money.transfer.dto.AccountDetailsDto;
import com.money.transfer.dto.AccountStatusDto;
import com.money.transfer.dto.AccountTypeDto;
import com.money.transfer.model.AccountDetails;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

@Component
public class AccountDetailsConverter implements Converter<List<AccountDetails>, List<AccountDetailsDto>> {
    @Override
    public List<AccountDetailsDto> convert(List<AccountDetails> source) {
        List<AccountDetailsDto> lstAccountDetailsDto = new ArrayList<>();
        source.forEach(accountDetails -> {
            AccountDetailsDto accountDetailsDto = new AccountDetailsDto();
            BeanUtils.copyProperties(accountDetails, accountDetailsDto);
            AccountStatusDto accountStatusDto = new AccountStatusDto();
            BeanUtils.copyProperties(accountDetails.getAccountStatus(), accountStatusDto);
            AccountTypeDto accountTypeDto = new AccountTypeDto();
            BeanUtils.copyProperties(accountDetails.getAccountType(), accountTypeDto);

            accountDetailsDto.setAccountType(accountTypeDto);
            accountDetailsDto.setAccountStatus(accountStatusDto);
            lstAccountDetailsDto.add(accountDetailsDto);
        });
        return lstAccountDetailsDto;
    }
}
