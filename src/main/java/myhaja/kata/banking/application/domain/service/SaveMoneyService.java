package myhaja.kata.banking.application.domain.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import myhaja.kata.banking.application.domain.model.Account;
import myhaja.kata.banking.application.port.in.SaveMoneyCommand;
import myhaja.kata.banking.application.port.in.SaveMoneyUseCase;
import myhaja.kata.banking.application.port.out.LoadAccountPort;
import myhaja.kata.banking.application.port.out.UpdateAccountStatePort;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
@Transactional
public class SaveMoneyService implements SaveMoneyUseCase {

    private final LoadAccountPort loadAccountPort;
    private final UpdateAccountStatePort updateAccountStatePort;

    @Override
    public boolean saveMoney(SaveMoneyCommand command) {
        Account sourceAccount = loadAccountPort.loadAccount(command.targetAccountId());
        Account.AccountId sourceAccountId = sourceAccount.getId()
                .orElseThrow(() -> new IllegalStateException("expected source account ID not to be empty"));
        sourceAccount.deposit(command.money(), sourceAccountId);
        updateAccountStatePort.updateActivities(sourceAccount);
        return true;
    }
}
