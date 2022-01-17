package com.money.transfer.repository;

import com.money.transfer.model.TransactionDetails;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;

public interface TransactionRepository extends CrudRepository<TransactionDetails, Long> {
    @Query(value = "SELECT SUM(txn.txnAomunt) FROM TransactionDetails txn WHERE txn.sourceAccountNo =:accountNo AND txn.txnDate =:currentDate")
    Double sumOfDailyTxnAmountByAccountNo(String accountNo, Date currentDate);
}
