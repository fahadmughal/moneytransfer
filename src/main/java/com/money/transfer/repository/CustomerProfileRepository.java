package com.money.transfer.repository;

import com.money.transfer.model.CustomerProfile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CustomerProfileRepository extends CrudRepository<CustomerProfile, Long> {
    boolean existsByCustomerId(String customerId);
    CustomerProfile findByCustomerId(String customerId);

}
