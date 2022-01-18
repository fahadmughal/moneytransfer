package com.money.transfer.repository;

import com.money.transfer.model.CustomerProfile;
import org.springframework.data.repository.CrudRepository;

public interface CustomerProfileRepository extends CrudRepository<CustomerProfile, Long> {
    boolean existsCustomerProfileByCustomerId(String customerId);
}
