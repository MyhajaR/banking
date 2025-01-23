package myhaja.kata.banking.application.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Account {

    public  AccountId id;

    @Getter
    private final Money baselineBalance;

    @Getter private final Statement statement;

    public Optional<AccountId> getId(){
        return Optional.ofNullable(this.id);
    }

    public static Account withId(
            AccountId accountId,
            Money baselineBalance,
            Statement statement) {
        return new Account(accountId, baselineBalance, statement);
    }

    public void deposit(Money money, AccountId sourceAccountId) {
        Activity deposit = new Activity(
                this.id,
                sourceAccountId,
                this.id,
                LocalDateTime.now(),
                money);
        this.statement.addActivity(deposit);
    }

    @Value
    public static class AccountId {
        private Long value;
    }
}
