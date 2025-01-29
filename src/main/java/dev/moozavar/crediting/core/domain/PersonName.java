package dev.moozavar.crediting.core.domain;

import dev.moozavar.crediting.core.domain.exception.DomainException;
import dev.moozavar.crediting.core.domain.exception.DomainMessages;

public record PersonName(String name) {
    public PersonName {
        if (name == null) throw new DomainException(DomainMessages.DOMAIN_CUSTOMER_NAME_NULL);
        if (name.matches(".*\\d.*") || name.length() < 2)
            throw new DomainException(DomainMessages.DOMAIN_CUSTOMER_NAME_INVALID);
    }
}
