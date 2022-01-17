package com.money.transfer.repository;

import com.money.transfer.model.AccountDetails;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.LockModeType;
import java.util.List;

public interface AccountDetailsRepository extends CrudRepository<AccountDetails, Long> {
    List<AccountDetails> findAll();
    /** TODO PESSIMISTIC READ implemented for handling concurrent txns*/
    @Lock(LockModeType.PESSIMISTIC_READ)
    AccountDetails findByAccountNo(String accountNo);
}
