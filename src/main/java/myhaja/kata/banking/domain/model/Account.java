package myhaja.kata.banking.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import myhaja.kata.banking.domain.exception.WithdrawException;

import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Account {

    public  Long id;

    @Getter
    private final Money baselineBalance;

    @Getter
    private final Statement statement;

    public Optional<Long> getId(){
        return Optional.ofNullable(this.id);
    }

    public static Account withId(
            Long accountId,
            Money baselineBalance,
            Statement statement) {
        return new Account(accountId, baselineBalance, statement);
    }

    public void deposit(Money money, Long sourceAccountId) {
        Activity deposit = new Activity(
                this.id,
                sourceAccountId,
                this.id,
                LocalDateTime.now(),
                money);
        this.statement.addActivity(deposit);
    }

    public void withdraw(Money money, Long targetAccountId) {

        if (!mayWithdraw(money)) {
            throw new WithdrawException(this.calculateBalance().getAmount());
        }

        Activity withdrawal = new Activity(
                this.id,
                this.id,
                targetAccountId,
                LocalDateTime.now(),
                money);
        this.statement.addActivity(withdrawal);
    }

    private boolean mayWithdraw(Money money) {
        return Money.subtract(
                        this.calculateBalance(),
                        money
                ).isPositiveOrZero();
    }

    /**
     * Calculates the total balance of the account by adding the activity values to the baseline balance.
     */
    public Money calculateBalance() {
        return Money.add(
                this.baselineBalance,
                this.statement.calculateBalance(this.id));
    }
}
