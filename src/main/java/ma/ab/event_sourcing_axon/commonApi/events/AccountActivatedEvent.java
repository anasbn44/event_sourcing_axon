package ma.ab.event_sourcing_axon.commonApi.events;

import lombok.Getter;
import ma.ab.event_sourcing_axon.commonApi.eums.AccountStatus;

import java.util.Date;

@Getter
public class AccountActivatedEvent extends BaseEvent<String>{
    private AccountStatus status;

    public AccountActivatedEvent(String id, Date date, AccountStatus status) {
        super(id, date);
        this.status = status;
    }
}
