package myhaja.kata.banking.domain.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@RequiredArgsConstructor
public class Activity {

    @Getter
    private Long id;

    @Getter
    @NonNull
    private final Long ownerAccountId;

    @Getter
    @NonNull
    private final Long sourceAccountId;

    @Getter
    @NonNull
    private final Long targetAccountId;

    @Getter
    @NonNull
    private final LocalDateTime date;

    @Getter
    @NonNull
    private final Money money;

    @Value
    public static class ActivityId {
        private final Long value;
    }

    public Activity(
            @NonNull Long ownerAccountId,
            @NonNull Long sourceAccountId,
            @NonNull Long targetAccountId,
            @NonNull LocalDateTime date,
            @NonNull Money money) {
        this.id = null;
        this.ownerAccountId = ownerAccountId;
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.date = date;
        this.money = money;
    }
}
