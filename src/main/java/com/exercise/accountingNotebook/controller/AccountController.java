package com.exercise.accountingNotebook.controller;

import com.exercise.accountingNotebook.model.Account;
import com.exercise.accountingNotebook.model.transaction.Request;
import com.exercise.accountingNotebook.model.transaction.Transaction;
import com.exercise.accountingNotebook.service.AccountTransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@Slf4j
@Validated
@CrossOrigin
@RestController
@RequestMapping("/accountingNotebook/api")
@Api(value="Accounting Notebook", description="Let's start saving accounting transactions.")
public class AccountController {
    private final AccountTransactionService accountService;

    @Autowired
    public AccountController (AccountTransactionService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/transactions/{accountId}")
    @ResponseBody
    @ResponseStatus
    @ApiOperation(value = "Return all Transaction created on an Account")
    public ResponseEntity<?> getTransactions(@PathVariable Long accountId) {
        return ResponseEntity.ok(accountService.getAllTransactions(accountId));
    }

    @PostMapping("/save/{accountId}")
    @ResponseBody
    @ResponseStatus
    @ApiOperation(value = "Return Transaction created", response = Transaction.class)
    public ResponseEntity<?> saveTransaction(@RequestBody Request requestTransaction, @PathVariable Long accountId) {
        return ResponseEntity.ok(accountService.createAccountTransaction(requestTransaction, accountId));
    }

    @GetMapping("/accountData/{userId}")
    @ResponseBody
    @ResponseStatus
    @ApiOperation(value = "Return Account info by userId", response = Account.class)
    public ResponseEntity<?> getAccountData(@PathVariable Long userId) {
        return ResponseEntity.ok(accountService.getDataByUserId(userId));
    }

    @GetMapping("/transaction/{id}")
    @ResponseBody
    @ResponseStatus
    @ApiOperation(value = "Return Transaction By Id")
    public ResponseEntity<?> getTransaction(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getTransactionById(id));
    }

    @GetMapping("/balance/{id}")
    @ResponseBody
    @ResponseStatus
    @ApiOperation(value = "Return Account balance By Id")
    public ResponseEntity<?> getBalance(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getBalanceById(id));
    }
}
