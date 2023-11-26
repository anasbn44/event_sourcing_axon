package ma.ab.event_sourcing_axon.query.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.ab.event_sourcing_axon.commonApi.queries.GetAccountByIdQuery;
import ma.ab.event_sourcing_axon.commonApi.queries.GetAllAccountsQuery;
import ma.ab.event_sourcing_axon.query.entities.Account;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/query/accounts")
@AllArgsConstructor
@Slf4j
public class AccountQueryController {
    private QueryGateway queryGateway;

    @GetMapping("/allAccounts")
    public List<Account> accountList(){
        List<Account> response = queryGateway.query(new GetAllAccountsQuery(), ResponseTypes.multipleInstancesOf(Account.class)).join();
        return response;
    }

    @GetMapping("/byId/{id}")
    public Account getAccount(@PathVariable String id){
        Account response = queryGateway.query(new GetAccountByIdQuery(id), ResponseTypes.instanceOf(Account.class)).join();
        return response;
    }
}
