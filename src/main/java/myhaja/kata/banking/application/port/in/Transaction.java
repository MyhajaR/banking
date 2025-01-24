package myhaja.kata.banking.application.port.in;

import jakarta.validation.constraints.NotNull;
import myhaja.kata.banking.domain.model.Money;

public record Transaction(
        @NotNull Long sourceAccountId,
        @NotNull Long targetAccountId,
        @NotNull Money money
) {
    public Transaction(
            Long sourceAccountId,
            Long targetAccountId,
            Money money) {
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.money = money;
        // validate(this);
    }
}
