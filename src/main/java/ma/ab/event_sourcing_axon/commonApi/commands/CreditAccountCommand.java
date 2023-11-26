package ma.ab.event_sourcing_axon.commonApi.commands;

import lombok.Getter;

@Getter
public class CreditAccountCommand extends  BaseCommand<String>{
    private double amount;
    private String currency;
    public CreditAccountCommand(String id, double amount, String currency) {
        super(id);
        this.amount = amount;
        this.currency = currency;
    }
}
