package myhaja.kata.banking.adapter.out.persistence;

import myhaja.kata.banking.domain.model.Account;
import myhaja.kata.banking.domain.model.Activity;
import myhaja.kata.banking.domain.model.Money;
import myhaja.kata.banking.domain.model.Statement;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class AccountMapper {

    public Account mapToDomainEntity(AccountJpaEntity accountFromDataBase, List<ActivityJpaEntity> activities, BigDecimal withdrawalBalance, BigDecimal depositBalance) {
        Money baseLineBalance = Money.subtract(
                Money.of(depositBalance),
                Money.of(withdrawalBalance)
        );
        return Account.withId(
                accountFromDataBase.getId(),
                baseLineBalance,
                mapToStatement(activities)
        );
    }

    private Statement mapToStatement(List<ActivityJpaEntity> activities) {
        List<Activity> mappedActivities = new ArrayList<>();

        for (ActivityJpaEntity activity : activities) {
            mappedActivities.add(new Activity(
                    activity.getId(),
                    activity.getOwnerAccountId(),
                    activity.getSourceAccountId(),
                    activity.getTargetAccountId(),
                    activity.getTimestamp(),
                    Money.of(activity.getAmount())));
        }

        return new Statement(mappedActivities);
    }

    public ActivityJpaEntity mapToJpaEntity(Activity activity) {
        return new ActivityJpaEntity(
                activity.getId() == null ? null : activity.getId(),
                activity.getDate(),
                activity.getOwnerAccountId(),
                activity.getSourceAccountId(),
                activity.getTargetAccountId(),
                activity.getMoney().getAmount());
    }
}
