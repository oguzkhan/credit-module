package dev.moozavar.crediting.core.application.mapper;

import java.math.BigDecimal;
import java.util.UUID;

public record PayLoanResponse(UUID customerId,
                              UUID loanId,
                              int numberOfPaidInstallments,
                              BigDecimal totalAmountSpent,
                              boolean allInstallmentsPaid) {
}
