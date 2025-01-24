package myhaja.kata.banking.application;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import myhaja.kata.banking.domain.exception.DepositException;
import myhaja.kata.banking.domain.exception.WithdrawException;
import myhaja.kata.banking.domain.model.Account;
import myhaja.kata.banking.domain.model.Activity;
import myhaja.kata.banking.domain.model.Money;
import myhaja.kata.banking.application.port.in.Transaction;
import myhaja.kata.banking.application.port.in.WithDrawRequest;
import myhaja.kata.banking.application.port.out.LoadAccountPort;
import myhaja.kata.banking.application.port.out.UpdateAccountStatePort;
import myhaja.kata.banking.domain.service.AccountServicePort;
import org.springframework.stereotype.Component;

import java.util.List;


@RequiredArgsConstructor
@Component
public class AccountService implements AccountServicePort {

    private final LoadAccountPort loadAccountPort;
    private final UpdateAccountStatePort updateAccountStatePort;

    @Override
    @Transactional
    public boolean saveMoney(Transaction transaction) {
        Account sourceAccount = loadAccountPort.loadAccount(transaction.targetAccountId());
        Long sourceAccountId = sourceAccount.getId()
                .orElseThrow(() -> new DepositException("expected source account ID not to be empty"));
        sourceAccount.deposit(transaction.money(), sourceAccountId);
        updateAccountStatePort.updateActivities(sourceAccount);
        return true;
    }

    @Override
    @Transactional
    public void withdraw(WithDrawRequest request) {
        Account sourceAccount = loadAccountPort.loadAccount(request.accountId());
        Long sourceAccountId = sourceAccount.getId()
                .orElseThrow(() -> new WithdrawException("expected source account ID not to be empty"));
        sourceAccount.withdraw(Money.of(request.amount()), sourceAccountId);
        updateAccountStatePort.updateActivities(sourceAccount);
    }

    @Override
    public List<Activity> getStatement(Long accountId) {
        Account sourceAccount = loadAccountPort.loadAccount(accountId);
        return sourceAccount.getStatement().getActivities();
    }
}
