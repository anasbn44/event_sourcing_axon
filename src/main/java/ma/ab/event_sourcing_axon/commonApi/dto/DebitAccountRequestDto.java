package ma.ab.event_sourcing_axon.commonApi.dto;

import lombok.Data;

@Data
public class DebitAccountRequestDto {
    private String accountId;
    private double amount;
    private String currency;
}
