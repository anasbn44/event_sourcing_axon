package ma.ab.event_sourcing_axon.query.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.ab.event_sourcing_axon.commonApi.eums.OperationType;
import ma.ab.event_sourcing_axon.commonApi.events.AccountActivatedEvent;
import ma.ab.event_sourcing_axon.commonApi.events.AccountCreatedEvent;
import ma.ab.event_sourcing_axon.commonApi.events.AccountCreditedEvent;
import ma.ab.event_sourcing_axon.commonApi.events.AccountDebitedEvent;
import ma.ab.event_sourcing_axon.commonApi.queries.GetAccountByIdQuery;
import ma.ab.event_sourcing_axon.commonApi.queries.GetAllAccountsQuery;
import ma.ab.event_sourcing_axon.query.entities.Account;
import ma.ab.event_sourcing_axon.query.entities.Operation;
import ma.ab.event_sourcing_axon.query.repositories.AccountRepository;
import ma.ab.event_sourcing_axon.query.repositories.OperationRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class AccountServiceHandler {
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;

    @EventHandler
    public void on(AccountCreatedEvent event){
        log.info("**AccountCreatedEvent**");
        accountRepository.save(new Account(
                event.getId(),
                event.getInitialBalance(),
                event.getStatus(),
                event.getCurrency(),
                null
        ));
    }

    @EventHandler
    public void on(AccountActivatedEvent event){
        log.info("**AccountActivatedEvent**");
        Account account = accountRepository.findById(event.getId()).get();
        account.setStatus(event.getStatus());
        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountDebitedEvent event){
        log.info("**AccountDebitedEvent**");
        Account account = accountRepository.findById(event.getId()).get();
        Operation operation = new Operation();
        operation.setAmount(event.getAmount());
        operation.setDate(event.getDate());
        operation.setType(OperationType.DEBIT);
        operation.setAccount(account);
        operationRepository.save(operation);
        account.setBalance(account.getBalance() - event.getAmount());
        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountCreditedEvent event){
        log.info("**AccountCreditedEvent**");
        Account account = accountRepository.findById(event.getId()).get();
        Operation operation = new Operation();
        operation.setAmount(event.getAmount());
        operation.setDate(event.getDate());
        operation.setType(OperationType.CREDIT);
        operation.setAccount(account);
        operationRepository.save(operation);
        account.setBalance(account.getBalance() + event.getAmount());
        accountRepository.save(account);
    }

    @QueryHandler
    public List<Account> on(GetAllAccountsQuery query){
        return accountRepository.findAll();
    }

    @QueryHandler
    public Account on(GetAccountByIdQuery query){
        return accountRepository.findById(query.getId()).get();
    }
}
