package myhaja.kata.banking.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void deposit_ShouldAddActivityStatement_AndUpdateBalanceCalculation() {
        // Given
        var defaultBaseLineAmount = BigDecimal.valueOf(100);
        var activity = new Activity(1L, 25L, 35L, 25L, LocalDateTime.now(), Money.of(defaultBaseLineAmount));
        var account = Account.withId(
            1L,
            Money.of(defaultBaseLineAmount),
            new Statement(activity)
        );
        // On teste qu'il y a qu'une seule activité par défaut avec la valeur correspondante
        assertEquals(account.getStatement().getActivities().size(), 1);
        assertEquals(account.calculateBalance().getAmount(), BigDecimal.valueOf(100));

        // When
        account.deposit(Money.of(BigDecimal.valueOf(50)), 75L);

        // Then
        // La nouvelle activité est bien ajoutée et le résultat du calcul de la balance est bien à jour (100 + 50 = 150 )
        assertEquals(account.getStatement().getActivities().size(), 2);
        assertEquals(account.calculateBalance().getAmount(), BigDecimal.valueOf(150));
    }

    @Test
    void withdraw_ShouldAddActivityStatement_AndUpdateBalanceCalculation() {

        // Given
        var defaultBaseLineAmount = BigDecimal.valueOf(100);
        var activity = new Activity(1L, 25L, 35L, 25L, LocalDateTime.now(), Money.of(BigDecimal.valueOf(50)));
        var activity2 = new Activity(2L, 25L, 36L, 25L, LocalDateTime.now(), Money.of(BigDecimal.valueOf(50)));
        var account = Account.withId(
                1L,
                Money.of(defaultBaseLineAmount),
                new Statement(activity, activity2)
        );
        assertEquals(account.getStatement().getActivities().size(), 2);
        assertEquals(account.calculateBalance().getAmount(), BigDecimal.valueOf(100));

        // When
        account.withdraw(Money.of(BigDecimal.valueOf(25)), 25L);

        // Then
        // La nouvelle activité est bien ajoutée et le résultat du calcul de la balance est bien à jour (100 - 25 = 75 )
        assertEquals(account.getStatement().getActivities().size(), 3);
        assertEquals(account.calculateBalance().getAmount(), BigDecimal.valueOf(75));
    }

    @Test
    void calculateBalance() {
    }
}