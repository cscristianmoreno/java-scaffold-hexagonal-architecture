package co.com.bancolombia.api.exceptions;

public class InvalidHashException extends RuntimeException {
    public InvalidHashException(String message) {
        super(message);
    }
}
