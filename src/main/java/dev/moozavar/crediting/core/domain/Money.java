package dev.moozavar.crediting.core.domain;

import dev.moozavar.crediting.core.domain.exception.DomainException;
import dev.moozavar.crediting.core.domain.exception.DomainMessages;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public record Money(BigDecimal amount) {

    public Money {
        if (amount == null) throw new DomainException(DomainMessages.DOMAIN_MONEY_AMOUNT_NULL);
    }

    public static Money of(BigDecimal amount) {
        return new Money(amount);
    }

    public static final Money ZERO = new Money(BigDecimal.ZERO);

    public boolean lessThan(Money otherAmount) {
        return amount.compareTo(otherAmount.amount) == -1;
    }

    public boolean lessThanOrEqual(Money otherAmount) {
        return amount.compareTo(otherAmount.amount) <= 0;
    }

    public boolean greaterThan(Money otherAmount) {
        return amount.compareTo(otherAmount.amount) == 1;
    }

    public boolean greaterThanOrEqual(Money otherAmount) {
        return amount.compareTo(otherAmount.amount) >= 0;
    }

    public boolean equal(Money otherAmount) {
        return Objects.equals(amount, otherAmount.amount);
    }

    public Money add(Money otherAmount) {
        return Money.of(amount.add(otherAmount.amount));
    }

    public Money subtract(Money otherAmount) {
        return Money.of(amount.subtract(otherAmount.amount));
    }

    public Money divide(int divisor) {
        return Money.of(amount.divide(BigDecimal.valueOf(divisor),
                CreditConstants.MONEY_SCALE, RoundingMode.HALF_EVEN));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        // 2 decimal scale is enough to be equal
        BigDecimal current = amount.setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal other = money.amount.setScale(2, RoundingMode.HALF_EVEN);
        return current.compareTo(other) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(amount);
    }
}
