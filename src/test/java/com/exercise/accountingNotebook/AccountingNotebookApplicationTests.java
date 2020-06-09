package com.exercise.accountingNotebook;

import com.exercise.accountingNotebook.model.Account;
import com.exercise.accountingNotebook.model.User;
import com.exercise.accountingNotebook.model.transaction.Request;
import com.exercise.accountingNotebook.model.transaction.Transaction;
import com.exercise.accountingNotebook.model.transaction.Type;
import com.exercise.accountingNotebook.repository.AccountRepository;
import com.exercise.accountingNotebook.service.AccountTransactionService;
import com.exercise.accountingNotebook.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AccountingNotebookApplicationTests {
	@Autowired
	private AccountTransactionService accountService;
	@Autowired
	private UserService userService;
	@Autowired
	private AccountRepository accountRepository;

	private static final String TEST_FAIL_TRANSACTION = "Could't create a Debit Transaction. The Balance shouldn't be zero.";
	private static final String TEST_DEBIT_TRANSACTION = "Debit Transaction created Successfully.";
	private static final String TEST_CREDIT_TRANSACTION = "Credit Transaction created Successfully.";


	Optional<User> user;
	List<Transaction> transactions;
	Account account;
	Request failTransaction;
	Request goodDebitTransaction;
	Request goodCreditTransaction;

	@Value("${initializer.user.name}")
	private String name;
	@Value("${initializer.user.codeId}")
	private String codeId;

	@Before
	public void setUp() {
		user = userService.getUser(1L);
		transactions = accountService.getAllTransactions(1L);
		account = accountRepository.findById(1L).get();
		goodDebitTransaction = new Request(new BigDecimal(-10000.00), Type.DEBIT);
		failTransaction = new Request(new BigDecimal(-70000.00), Type.DEBIT);
		goodCreditTransaction = new Request(new BigDecimal(25000.00), Type.CREDIT);
	}

	@Test
	public void contextLoads() {
		assertThat(user.get().getName(), equalTo(this.name));
		assertThat(user.get().getCodeId(), equalTo(this.codeId));
		assertThat(transactions, hasSize(1));

		List<Transaction> accountTransactions = accountService.getDataByUserId(user.get().getId()).get().getAccountTransactions();
		assertThat(accountTransactions, hasSize(1));

		BigDecimal balance = new BigDecimal(accountService.getBalanceById(account.getId()).toString());
		assertEquals(balance, new BigDecimal("50000.00"));
	}

	@Test
	public void shouldDebitAndGetNewBalance() {
		accountService.createAccountTransaction(goodDebitTransaction, account.getId());
		List<Transaction> accountTransactions = accountService.getDataByUserId(user.get().getId()).get().getAccountTransactions();
		assertThat(accountTransactions, hasSize(2));

		BigDecimal balance = new BigDecimal(accountService.getBalanceById(account.getId()).toString());
		assertEquals(balance, new BigDecimal("40000.00"));
		assertThat(accountTransactions.get(1).getDescription(), equalTo(TEST_DEBIT_TRANSACTION));
	}

	@Test
	public void shouldNotDebit() {
		accountService.createAccountTransaction(failTransaction, account.getId());
		List<Transaction> accountTransactions = accountService.getDataByUserId(user.get().getId()).get().getAccountTransactions();
		assertThat(accountTransactions, hasSize(2));

		BigDecimal balance = new BigDecimal(accountService.getBalanceById(account.getId()).toString());
		assertEquals(balance, new BigDecimal("50000.00"));
		assertThat(accountTransactions.get(1).getDescription(), equalTo(TEST_FAIL_TRANSACTION));
	}

	@Test
	public void shouldCreditAndGetNewBalance() {
		accountService.createAccountTransaction(goodCreditTransaction, account.getId());
		List<Transaction> accountTransactions = accountService.getDataByUserId(user.get().getId()).get().getAccountTransactions();
		assertThat(accountTransactions, hasSize(2));

		BigDecimal balance = new BigDecimal(accountService.getBalanceById(account.getId()).toString());
		assertEquals(balance, new BigDecimal("75000.00"));
		assertThat(accountTransactions.get(1).getDescription(), equalTo(TEST_CREDIT_TRANSACTION));
	}
}
