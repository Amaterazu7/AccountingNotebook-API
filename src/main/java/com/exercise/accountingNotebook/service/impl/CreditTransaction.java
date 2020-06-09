package com.exercise.accountingNotebook.service.impl;

import com.exercise.accountingNotebook.exception.TransactionException;
import com.exercise.accountingNotebook.model.Account;
import com.exercise.accountingNotebook.model.transaction.Request;
import com.exercise.accountingNotebook.model.transaction.Status;
import com.exercise.accountingNotebook.model.transaction.Transaction;
import com.exercise.accountingNotebook.service.TransactionTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Slf4j
@Service
public class CreditTransaction implements TransactionTypeService {
    ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock accountLock = rwLock.writeLock();

    @Override
    public Transaction handlerTransaction(Request request, Account account) {
        Transaction creditTransaction = new Transaction();

        if (checkBalance(request, account.getTotalAmount())) {
            account.setTotalAmount( account.getTotalAmount().add(request.getAmount()) );
            creditTransaction.setStatus( Status.REGISTERED );
            creditTransaction.setDescription( "Credit Transaction created Successfully." );
        } else {
            creditTransaction.setStatus( Status.FAILED );
            creditTransaction.setDescription( "Could't create a Credit Transaction." );
        }
        // Mutex the object for write access
        setBalance(creditTransaction, request, account);

        return creditTransaction;
    }

    @Override
    public boolean checkBalance(Request request, BigDecimal accountBalance) {
        int res = request.getAmount().compareTo(new BigDecimal("0"));
        return ( res > 0 );
    }

    @Transactional
    @Override
    public void setBalance(Transaction creditTransaction, Request request, Account account) {
        this.accountLock.lock();
        try {
            creditTransaction.setAmount( request.getAmount() );
            creditTransaction.setType( request.getType() );
            creditTransaction.setAccount( account );

        } finally {
            this.accountLock.unlock();
        }

    }



}
