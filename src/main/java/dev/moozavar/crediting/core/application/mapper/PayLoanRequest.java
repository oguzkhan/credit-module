package dev.moozavar.crediting.core.application.mapper;

import java.math.BigDecimal;
import java.util.UUID;

public record PayLoanRequest(UUID loanId,
                             BigDecimal amount) {
}
