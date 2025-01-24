package myhaja.kata.banking.application.port.in;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public record WithDrawRequest(
    @NotNull Long accountId,
    @PositiveOrZero BigDecimal amount
){}
