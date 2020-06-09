package com.exercise.accountingNotebook.service.impl;

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
public class DebitTransaction implements TransactionTypeService {
    ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock accountLock = rwLock.writeLock();

    @Override
    public Transaction handlerTransaction(Request request, Account account) {
        Transaction debitTransaction = new Transaction();

        if (checkBalance(request, account.getTotalAmount())) {
            account.setTotalAmount( account.getTotalAmount().add(request.getAmount()) );
            debitTransaction.setStatus( Status.REGISTERED );
            debitTransaction.setDescription( "Debit Transaction created Successfully." );
        } else {
            debitTransaction.setStatus( Status.FAILED );
            debitTransaction.setDescription( "Could't create a Debit Transaction. The Balance shouldn't be zero." );
        }
        // Mutex the object for write access
        setBalance(debitTransaction, request, account);

        return debitTransaction;
    }

    @Override
    public boolean checkBalance(Request request, BigDecimal accountBalance) {
        BigDecimal zero = new BigDecimal("0");
        int resAmount = request.getAmount().compareTo(zero);
        int restBalance = ( accountBalance.add(request.getAmount()) ).compareTo(zero);

        return ( resAmount == -1 && restBalance == 1 );
    }


    @Transactional
    @Override
    public void setBalance(Transaction debitTransaction, Request request, Account account) {
        this.accountLock.lock();
        try {
            debitTransaction.setAmount( request.getAmount() );
            debitTransaction.setType( request.getType() );
            debitTransaction.setAccount( account );

        } finally {
            this.accountLock.unlock();
        }
    }
}
