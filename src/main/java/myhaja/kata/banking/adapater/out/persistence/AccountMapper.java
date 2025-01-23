package myhaja.kata.banking.adapater.out.persistence;

import myhaja.kata.banking.application.domain.model.Account;
import myhaja.kata.banking.application.domain.model.Activity;
import myhaja.kata.banking.application.domain.model.Money;
import myhaja.kata.banking.application.domain.model.Statement;
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
                new Account.AccountId(accountFromDataBase.getId()),
                baseLineBalance,
                mapToStatement(activities)
        );
    }

    private Statement mapToStatement(List<ActivityJpaEntity> activities) {
        List<Activity> mappedActivities = new ArrayList<>();

        for (ActivityJpaEntity activity : activities) {
            mappedActivities.add(new Activity(
                    new Activity.ActivityId(activity.getId()),
                    new Account.AccountId(activity.getOwnerAccountId()),
                    new Account.AccountId(activity.getSourceAccountId()),
                    new Account.AccountId(activity.getTargetAccountId()),
                    activity.getTimestamp(),
                    Money.of(activity.getAmount())));
        }

        return new Statement(mappedActivities);
    }

    public ActivityJpaEntity mapToJpaEntity(Activity activity) {
        return new ActivityJpaEntity(
                activity.getId() == null ? null : activity.getId().getValue(),
                activity.getDate(),
                activity.getOwnerAccountId().getValue(),
                activity.getSourceAccountId().getValue(),
                activity.getTargetAccountId().getValue(),
                activity.getMoney().getAmount());
    }
}
