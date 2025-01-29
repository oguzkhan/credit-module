package dev.moozavar.crediting.core.domain;

import dev.moozavar.crediting.core.domain.exception.DomainException;
import dev.moozavar.crediting.core.domain.exception.DomainMessages;

public record PersonSurname(String surname) {
    public PersonSurname {
        if (surname == null) throw new DomainException(DomainMessages.DOMAIN_CUSTOMER_SURNAME_NULL);
        if (surname.matches(".*\\d.*") || surname.length() < 2)
            throw new DomainException(DomainMessages.DOMAIN_CUSTOMER_SURNAME_INVALID);
    }
}
