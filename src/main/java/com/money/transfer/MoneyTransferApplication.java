package com.money.transfer;

import com.money.transfer.model.*;
import com.money.transfer.repository.AccountStatusRepository;
import com.money.transfer.repository.AccountTypeRepository;
import com.money.transfer.repository.CustomerProfileRepository;
import com.money.transfer.repository.CustomerTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@SpringBootApplication
public class MoneyTransferApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoneyTransferApplication.class, args);
    }

    @Bean /*TODO This function only use for insertion the default record in in-memory DB.*/
    public CommandLineRunner insertDefaultRecords(CustomerProfileRepository customerProfileRepository, AccountTypeRepository accountTypeRepository,
                                                  CustomerTypeRepository customerTypeRepository, AccountStatusRepository accountStatusRepository) {
        return (args) -> {
            // save some Account Type
            accountTypeRepository.save(new AccountType(1L, "Saving Account", "Saving Account Description"));
            accountTypeRepository.save(new AccountType(2L, "Saving Account", "Saving Account Description"));

            // save some Customer Type
            customerTypeRepository.save(new CustomerType(1L, "Retail Banking", "Retail Banking Desc"));
            customerTypeRepository.save(new CustomerType(2L, "Corporate Banking", "Corporate Banking Desc"));

            //save account status
            accountStatusRepository.save(new AccountStatus(1L, "Dormant"));
            accountStatusRepository.save(new AccountStatus(2L, "Closed"));
            accountStatusRepository.save(new AccountStatus(3L, "InActive"));
            accountStatusRepository.save(new AccountStatus(4L, "Active"));

            // save some Customer Profile
            // Retail customer, Current + Saving account creation
            List<AccountDetails> aliAccountDetails = new ArrayList<>();
            aliAccountDetails.add(new AccountDetails(1L, "Ali Current Account Title", "0919191919", "AED", new Date(), new Date(), 10000.0, 10000.0,
                    5000.00, accountTypeRepository.findById(1L).get(), accountStatusRepository.findById(4L).get()));
            aliAccountDetails.add(new AccountDetails(2L, "Ali Saving Account Title", "012313134", "USD", new Date(), new Date(), 10000.0, 10000.0,
                    5000.00, accountTypeRepository.findById(2L).get(), accountStatusRepository.findById(2L).get()));
            customerProfileRepository.save(new CustomerProfile(1L, "csId1", "Ali First Name", "Ali Last Name", "Ali's primary address",
                    "Ali's secondary address", "+971552321112", "+972332212223",
                    customerTypeRepository.findById(1L).get(), aliAccountDetails));

            //Corporate customer, Current account
            List<AccountDetails> ahmedAccountDetails = new ArrayList<>();
            ahmedAccountDetails.add(new AccountDetails(3L, "Ahmed Current Account Title", "01245464", "USD", new Date(), new Date(), 20000.0, 20000.0,
                    5000.00, accountTypeRepository.findById(1L).get(), accountStatusRepository.findById(4L).get()));
            customerProfileRepository.save(new CustomerProfile(2L, "csId2", "Ahmed First Name", "Ahmed Last Name", "Ahmed's primary address",
                    "Ahmed's secondary address", "+971552321112", "+972332212223",
                    customerTypeRepository.findById(2L).get(), ahmedAccountDetails));

            // Retail customer, Current account creation
            List<AccountDetails> johnAccountDetails = new ArrayList<>();
            johnAccountDetails.add(new AccountDetails(4L, "John Current Account Title", "013564839", "AED", new Date(), new Date(),5000.0, 5000.0,
                    5000.00, accountTypeRepository.findById(1L).get(), accountStatusRepository.findById(1L).get()));
            customerProfileRepository.save(new CustomerProfile(3L, "csId3", "John First Name", "John Last Name", "John's primary address",
                    "John's secondary address", "+971552321112", "+972332212223",
                    customerTypeRepository.findById(1L).get(), johnAccountDetails));
        };
    }

}
