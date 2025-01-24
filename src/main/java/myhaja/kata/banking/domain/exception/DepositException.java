package myhaja.kata.banking.domain.exception;

public class DepositException extends RuntimeException {
    public DepositException(String message) {
        super(message);
    }
}
