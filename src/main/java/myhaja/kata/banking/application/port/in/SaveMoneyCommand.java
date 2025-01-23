package myhaja.kata.banking.application.port.in;

import jakarta.validation.constraints.NotNull;
import myhaja.kata.banking.application.domain.model.Account;
import myhaja.kata.banking.application.domain.model.Money;

public record SaveMoneyCommand(
        @NotNull Account.AccountId sourceAccountId,
        @NotNull Account.AccountId targetAccountId,
        @NotNull Money money
) {
    public SaveMoneyCommand(
            Account.AccountId sourceAccountId,
            Account.AccountId targetAccountId,
            Money money) {
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.money = money;
        // validate(this);
    }
}
