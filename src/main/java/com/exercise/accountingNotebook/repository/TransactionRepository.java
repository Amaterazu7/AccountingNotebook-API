package com.exercise.accountingNotebook.repository;

import com.exercise.accountingNotebook.model.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Lock(LockModeType.OPTIMISTIC)
    Transaction save(Transaction transaction);
}
