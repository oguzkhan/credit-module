package dev.moozavar.crediting.core.domain;

import dev.moozavar.crediting.core.domain.exception.DomainException;
import dev.moozavar.crediting.core.domain.exception.DomainMessages;

public record InterestRate(Double rate) {

    public static final Double lowerLimit = 0.1;
    public static final Double upperLimit = 0.5;

    public InterestRate {
        if (rate == null) throw new DomainException(DomainMessages.INTEREST_RATE_NULL);
        if (rate < lowerLimit || rate > upperLimit) throw new DomainException(DomainMessages.INTEREST_RATE_INVALID);
    }
}
