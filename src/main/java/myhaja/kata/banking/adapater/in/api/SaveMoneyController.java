package myhaja.kata.banking.adapater.in.api;

import lombok.RequiredArgsConstructor;
import myhaja.kata.banking.application.domain.model.Account;
import myhaja.kata.banking.application.domain.model.Money;
import myhaja.kata.banking.application.port.in.SaveMoneyCommand;
import myhaja.kata.banking.application.port.in.SaveMoneyUseCase;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
public class SaveMoneyController {

    private final SaveMoneyUseCase saveMoneyUseCase;

    @PostMapping(path = "/accounts/save/{sourceAccountId}/{targetAccountId}/{amount}")
    void sendMoney(
            @PathVariable("sourceAccountId") Long sourceAccountId,
            @PathVariable("targetAccountId") Long targetAccountId,
            @PathVariable("amount") BigDecimal amount) {

        SaveMoneyCommand command = new SaveMoneyCommand(
                new Account.AccountId(sourceAccountId),
                new Account.AccountId(targetAccountId),
                Money.of(amount));

        saveMoneyUseCase.saveMoney(command);
    }
}
