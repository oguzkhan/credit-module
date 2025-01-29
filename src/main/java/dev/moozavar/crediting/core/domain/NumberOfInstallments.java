package dev.moozavar.crediting.core.domain;

import dev.moozavar.crediting.core.domain.exception.DomainException;
import dev.moozavar.crediting.core.domain.exception.DomainMessages;

import java.util.HashMap;
import java.util.Map;

public enum NumberOfInstallments {

    NOI_6(6),
    NOI_9(9),
    NOI_12(12),
    NOI_24(24);

    public final int value;

    private static final Map<Integer, NumberOfInstallments> map = new HashMap<>();

    static {
        for (NumberOfInstallments noi : values()) {
            map.put(noi.value, noi);
        }
    }

    NumberOfInstallments(final int value) {
        this.value = value;
    }

    public static NumberOfInstallments valueOf(int value) {
        NumberOfInstallments noi = map.get(value);
        if (noi == null) throw new DomainException(DomainMessages.DOMAIN_NUMBER_OF_INSTALLMENT_INVALID);
        return noi;
    }

}
