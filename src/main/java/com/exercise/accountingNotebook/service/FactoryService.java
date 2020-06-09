package com.exercise.accountingNotebook.service;

import com.exercise.accountingNotebook.exception.TransactionException;
import com.exercise.accountingNotebook.model.transaction.Type;
import com.exercise.accountingNotebook.service.impl.CreditTransaction;
import com.exercise.accountingNotebook.service.impl.DebitTransaction;
import org.springframework.stereotype.Service;

@Service
public class FactoryService {
    public static TransactionTypeService createTransaction(Type transactionType) {
        switch (transactionType) {
            case DEBIT:
                return new DebitTransaction();
            case CREDIT:
                return new CreditTransaction();
            default:
                throw new TransactionException("Not supported transaction Type, only should be CREDIT or DEBIT.");
        }
    }
}
