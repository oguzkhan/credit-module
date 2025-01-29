package dev.moozavar.crediting.core.application.mapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record LoanInstallmentResponse(UUID id,
                                      UUID loanId,
                                      BigDecimal amount,
                                      BigDecimal paidAmount,
                                      LocalDate dueDate,
                                      LocalDate paymentDate,
                                      boolean isPaid) {

}
