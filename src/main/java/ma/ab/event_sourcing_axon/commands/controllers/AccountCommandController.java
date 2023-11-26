package ma.ab.event_sourcing_axon.commands.controllers;

import lombok.AllArgsConstructor;
import ma.ab.event_sourcing_axon.commonApi.commands.CreateAccountCommand;
import ma.ab.event_sourcing_axon.commonApi.commands.CreditAccountCommand;
import ma.ab.event_sourcing_axon.commonApi.commands.DebitAccountCommand;
import ma.ab.event_sourcing_axon.commonApi.dto.CreateAccountRequestDto;
import ma.ab.event_sourcing_axon.commonApi.dto.CreditAccountRequestDto;
import ma.ab.event_sourcing_axon.commonApi.dto.DebitAccountRequestDto;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = "/command/account")
@AllArgsConstructor
public class AccountCommandController {
    private CommandGateway commandGateway;
    private EventStore eventStore;
    @PostMapping(path = "/create")
    public CompletableFuture<String> createAccount(@RequestBody CreateAccountRequestDto request){
        CompletableFuture<String> response = commandGateway.send(new CreateAccountCommand(
                UUID.randomUUID().toString(),
                request.getInitialBalance(),
                request.getCurrency()
        ));
        return response;
    }

    @PutMapping(path = "/credit")
    public CompletableFuture<String> creditAccount(@RequestBody CreditAccountRequestDto request){
        CompletableFuture<String> response = commandGateway.send(new CreditAccountCommand(
                request.getAccountId(),
                request.getAmount(),
                request.getCurrency()
        ));
        return response;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception exception){
        ResponseEntity<String> entity = new ResponseEntity<>(
                exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
        return entity;
    }

    @GetMapping("/eventStore/{accountId}")
    public Stream eventStore(@PathVariable String accountId){
        return eventStore.readEvents(accountId).asStream();
    }

    @PutMapping(path = "/debit")
    public CompletableFuture<String> debitAccount(@RequestBody DebitAccountRequestDto request){
        CompletableFuture<String> response = commandGateway.send(new DebitAccountCommand(
                request.getAccountId(),
                request.getAmount(),
                request.getCurrency()
        ));
        return response;
    }
}
