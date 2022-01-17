package com.money.transfer.repository;

import com.money.transfer.model.CustomerType;
import org.springframework.data.repository.CrudRepository;

public interface CustomerTypeRepository extends CrudRepository<CustomerType, Long> {
}
