package com.money.transfer.repository;

import com.money.transfer.model.AccountStatus;
import org.springframework.data.repository.CrudRepository;

public interface AccountStatusRepository extends CrudRepository<AccountStatus, Long> {
}
