package com.exercise.accountingNotebook.service.impl;

import com.exercise.accountingNotebook.exception.TransactionException;
import com.exercise.accountingNotebook.model.Account;
import com.exercise.accountingNotebook.model.transaction.Request;
import com.exercise.accountingNotebook.model.transaction.Transaction;
import com.exercise.accountingNotebook.repository.AccountRepository;
import com.exercise.accountingNotebook.repository.TransactionRepository;
import com.exercise.accountingNotebook.service.AccountTransactionService;
import com.exercise.accountingNotebook.service.FactoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class AccountTransactionServiceImpl implements AccountTransactionService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    AccountTransactionServiceImpl(AccountRepository AccountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = AccountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Optional<Account> getDataByUserId(Long userId) {
        log.info("::: USER ID ::: " + userId);
        return accountRepository.findByUserId(userId);
    }

    @Override
    public Transaction createAccountTransaction(Request requestTransaction, Long accountId) {
        return accountRepository.findById(accountId)
                .map( account -> FactoryService.createTransaction(requestTransaction.getType()).handlerTransaction(requestTransaction, account) )
                .map( transaction -> saveAccountTransaction(transaction) )
                .orElseThrow( () -> new TransactionException(String.format("ERROR :: Error in transaction with account ID = ", accountId)) );
    }

    @Override
    public Transaction saveAccountTransaction(Transaction transaction) {
        accountRepository.save( transaction.getAccount() );
        return transactionRepository.save( transaction );
    }

    @Override
    public List<Transaction> getAllTransactions(Long accountId) {
        log.info("::: ACCOUNT ID ::: " + accountId);
        return accountRepository.findById(accountId).get().getAccountTransactions();
    }

    @Override
    public Optional<Transaction> getTransactionById(Long transactionId) {
        log.info("::: TRANSACTION ID ::: " + transactionId);
        return transactionRepository.findById(transactionId);
    }

    @Override
    public BigDecimal getBalanceById(Long accountId) {
        log.info("::: ACCOUNT ID ::: " + accountId);
        return  accountRepository.findById(accountId).get().getTotalAmount();
    }


}
