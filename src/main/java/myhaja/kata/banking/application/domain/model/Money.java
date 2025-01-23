package myhaja.kata.banking.application.domain.model;

import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class Money {

    public static Money ZERO = Money.of(BigDecimal.ZERO);

    @NonNull
    private final BigDecimal amount;

    public static Money of(BigDecimal value) {
        return new Money(value);
    }

    public static Money subtract(Money depositBalance, Money withdrawalBalance) {
        return new Money(depositBalance.amount.subtract(withdrawalBalance.amount));
    }
}
