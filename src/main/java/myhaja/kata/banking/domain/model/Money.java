package myhaja.kata.banking.domain.model;

import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class Money {

    public static Money ZERO = Money.of(BigDecimal.ZERO);

    @NonNull
    BigDecimal amount;

    public static Money of(BigDecimal value) {
        return new Money(value);
    }

    public static Money subtract(Money depositBalance, Money withdrawalBalance) {
        return Money.of(depositBalance.amount.subtract(withdrawalBalance.amount));
    }

    public static Money add(Money a, Money b) {
        return new Money(a.amount.add(b.amount));
    }

    public boolean isPositiveOrZero() {
        return this.amount.compareTo(BigDecimal.ZERO) >= 0;
    }
}
