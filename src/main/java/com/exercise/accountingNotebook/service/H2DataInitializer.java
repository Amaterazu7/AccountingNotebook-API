package com.exercise.accountingNotebook.service;

import com.exercise.accountingNotebook.model.Account;
import com.exercise.accountingNotebook.model.User;
import com.exercise.accountingNotebook.model.transaction.Status;
import com.exercise.accountingNotebook.model.transaction.Transaction;
import com.exercise.accountingNotebook.model.transaction.Type;
import com.exercise.accountingNotebook.repository.AccountRepository;
import com.exercise.accountingNotebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class H2DataInitializer implements ApplicationListener<ContextRefreshedEvent> {
    private final AccountTransactionService accountService;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    private boolean isReady = false;

    @Value("${initializer.user.name}")
    private String name;
    @Value("${initializer.user.surname}")
    private String surname;
    @Value("${initializer.user.codeId}")
    private String codeId;
    @Value("${initializer.user.email}")
    private String email;


    @Autowired
    public H2DataInitializer(AccountTransactionService accountService, AccountRepository accountRepository, UserRepository userRepository) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (isReady) { return; }
        createDefaultUser();
        isReady = true;
    }

    @Transactional
    protected void createDefaultUser() {
        Optional<User> propertiesUser = Optional.ofNullable(userRepository.findByName(name));

        if (!propertiesUser.isPresent()) {
            propertiesUser = Optional.of(new User(name, surname, codeId, email));
            User user = userRepository.save(propertiesUser.get());

            Account account = new Account();
            account.setUser(user);
            account.setTotalAmount(new BigDecimal(0.0));
            account = accountRepository.save(account);

            Transaction transaction = new Transaction(
                    new BigDecimal(50000.00), Type.CREDIT, Status.REGISTERED, "InitializerTransaction", account
            );

            account.setTotalAmount( account.getTotalAmount().add(transaction.getAmount()) );

            accountService.saveAccountTransaction(transaction);
        }

    }


}
