package com.exercise.accountingNotebook.service;

import com.exercise.accountingNotebook.model.Account;
import com.exercise.accountingNotebook.model.transaction.Request;
import com.exercise.accountingNotebook.model.transaction.Transaction;

import java.math.BigDecimal;

public interface TransactionTypeService {

    Transaction handlerTransaction(Request request, Account account);
    boolean checkBalance(Request request, BigDecimal accountBalance);
    void setBalance(Transaction transaction, Request request, Account account);
}
