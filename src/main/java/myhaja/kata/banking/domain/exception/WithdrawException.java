package myhaja.kata.banking.domain.exception;

import java.math.BigDecimal;

public class WithdrawException extends RuntimeException {

    public WithdrawException(String message) {
        super(message);
    }

    public WithdrawException(BigDecimal balance) {
        super("Error during withdraw, your account balance is " + balance);
    }
}
