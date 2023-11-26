package ma.ab.event_sourcing_axon.commonApi.exceptions;

public class AmountNegativeException extends RuntimeException {
    public AmountNegativeException(String message) {
        super(message);
    }
}
