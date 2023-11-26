package ma.ab.event_sourcing_axon.commonApi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class CreateAccountRequestDto {
    private double initialBalance;
    private String currency;
}
