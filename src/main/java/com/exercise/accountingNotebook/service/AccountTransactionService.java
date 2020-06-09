package com.exercise.accountingNotebook.service;

import com.exercise.accountingNotebook.model.Account;
import com.exercise.accountingNotebook.model.transaction.Request;
import com.exercise.accountingNotebook.model.transaction.Transaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccountTransactionService {
    /**
     * Given a userId,
     * will be retrieve a user Account.
     *
     * @param userId
     * @return Transaction
     */
    Optional<Account> getDataByUserId(Long userId);
    /**
     * Given a transaction, accountId
     * will be save a TransactionAccount for a User.
     *
     * @param requestTransaction, accountId
     * @return Transaction
     */
    Transaction createAccountTransaction(Request requestTransaction, Long accountId);
    /**
     * Given a transaction
     * will be save a Transaction for an Account and User.
     *
     * @param transaction
     * @return Transaction
     */
    Transaction saveAccountTransaction(Transaction transaction);
    /**
     * Given a accountId
     * will be retrieve a List of Transaction for an Account and User.
     *
     * @param accountId
     * @return List<Transaction>
     */
    List<Transaction> getAllTransactions(Long accountId);
    /**
     * Given a transactionId
     * will be retrieve a Transaction.
     *
     * @param transactionId
     * @return Transaction
     */
    Optional<Transaction> getTransactionById(Long transactionId);
    /**
     * Given a accountId
     * will be retrieve the account balance.
     *
     * @param accountId
     * @return AccountBalance
     */
    BigDecimal getBalanceById(Long accountId);
}
