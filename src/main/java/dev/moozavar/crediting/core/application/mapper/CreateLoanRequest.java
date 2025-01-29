package dev.moozavar.crediting.core.application.mapper;

import java.math.BigDecimal;
import java.util.UUID;


public record CreateLoanRequest(UUID customerId,
                                BigDecimal loanAmount,
                                Double interestRate,
                                int numberOfInstallments) {
}
