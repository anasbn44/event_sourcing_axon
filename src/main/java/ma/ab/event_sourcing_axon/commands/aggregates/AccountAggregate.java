package ma.ab.event_sourcing_axon.commands.aggregates;

import ma.ab.event_sourcing_axon.commonApi.commands.CreateAccountCommand;
import ma.ab.event_sourcing_axon.commonApi.commands.CreditAccountCommand;
import ma.ab.event_sourcing_axon.commonApi.commands.DebitAccountCommand;
import ma.ab.event_sourcing_axon.commonApi.eums.AccountStatus;
import ma.ab.event_sourcing_axon.commonApi.events.AccountActivatedEvent;
import ma.ab.event_sourcing_axon.commonApi.events.AccountCreatedEvent;
import ma.ab.event_sourcing_axon.commonApi.events.AccountCreditedEvent;
import ma.ab.event_sourcing_axon.commonApi.events.AccountDebitedEvent;
import ma.ab.event_sourcing_axon.commonApi.exceptions.AmountNegativeException;
import ma.ab.event_sourcing_axon.commonApi.exceptions.BalanceNotSufficientException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.Date;

@Aggregate
public class AccountAggregate {
    @AggregateIdentifier
    private String accountId;
    private double balance;
    private String currency;
    private AccountStatus status;

    public AccountAggregate() {
    }

    @CommandHandler
    public AccountAggregate(CreateAccountCommand createAccountCommand) {
        if(createAccountCommand.getInitialBalance() < 0) throw new RuntimeException("Negative initial balance(need to be positive)");

        AggregateLifecycle.apply(new AccountCreatedEvent(
                createAccountCommand.getId(),
                new Date(),
                createAccountCommand.getInitialBalance(),
                createAccountCommand.getCurrency(),
                AccountStatus.CREATED));
    }
    @EventSourcingHandler
    public void onCreateAccount(AccountCreatedEvent event){
        this.accountId = event.getId();
        this.balance = event.getInitialBalance();
        this.currency = event.getCurrency();
        this.status = AccountStatus.CREATED;
        AggregateLifecycle.apply(new AccountActivatedEvent(
                event.getId(),
                new Date(),
                AccountStatus.ACTIVATED
        ));
    }

    @EventSourcingHandler
    public void onActivatedAccount(AccountActivatedEvent event){
        this.status = event.getStatus();
    }

    @CommandHandler
    public void handle(CreditAccountCommand command){
        if(command.getAmount() < 0) throw new AmountNegativeException("Negative balance(should be positive)");
        AggregateLifecycle.apply(new AccountCreditedEvent(
                command.getId(),
                new Date(),
                command.getAmount(),
                command.getCurrency()
        ));
    }
    @EventSourcingHandler
    public void onCreditedAccount(AccountCreditedEvent event){
        this.balance += event.getAmount();
    }

    @CommandHandler
    public void handle(DebitAccountCommand command){
        if(command.getAmount() < 0) throw new AmountNegativeException("Negative balance(should be positive)");
        if(this.balance < command.getAmount()) throw new BalanceNotSufficientException("Balance not enough : " + this.balance);
        AggregateLifecycle.apply(new AccountDebitedEvent(
                command.getId(),
                new Date(),
                command.getAmount(),
                command.getCurrency()
        ));
    }
    @EventSourcingHandler
    public void onDebitedAccount(AccountDebitedEvent event){
        this.balance -= event.getAmount();
    }
}
