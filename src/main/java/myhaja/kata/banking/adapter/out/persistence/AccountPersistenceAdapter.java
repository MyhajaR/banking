package myhaja.kata.banking.adapter.out.persistence;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import myhaja.kata.banking.domain.model.Account;
import myhaja.kata.banking.domain.model.Activity;
import myhaja.kata.banking.application.port.out.LoadAccountPort;
import myhaja.kata.banking.application.port.out.UpdateAccountStatePort;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Component
public class AccountPersistenceAdapter implements
        LoadAccountPort,
        UpdateAccountStatePort {

    private final AccountRepository accountRepository;
    private final ActivityRepository activityRepository;
    private final AccountMapper accountMapper;

    @Override
    public Account loadAccount(Long accountId) {
        var accountFromDataBase = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));

        var activities = activityRepository.findByOwner(accountId);
        var withdrawalTotal = activityRepository
                .getWithdrawalBalance(
                        accountId)
                .orElse(BigDecimal.ZERO);
        var depositTotal = activityRepository
                .getDepositBalance(
                        accountId)
                .orElse(BigDecimal.ZERO);
        return accountMapper.mapToDomainEntity(
                accountFromDataBase,
                activities,
                withdrawalTotal,
                depositTotal
        );
    }

    @Override
    public void updateActivities(Account account) {
        for (Activity activity : account.getStatement().getActivities()) {
            if (activity.getId() == null) {
                activityRepository.save(accountMapper.mapToJpaEntity(activity));
            }
        }
    }
}
