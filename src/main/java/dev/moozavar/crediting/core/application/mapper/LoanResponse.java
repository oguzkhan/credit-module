package dev.moozavar.crediting.core.application.mapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record LoanResponse(UUID id,
                           UUID customerId,
                           BigDecimal loanAmount,
                           Double interestRate,
                           int numberOfInstallments,
                           LocalDate createDate,
                           boolean isPaid) {

}
