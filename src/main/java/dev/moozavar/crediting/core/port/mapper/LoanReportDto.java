package dev.moozavar.crediting.core.port.mapper;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Builder
public class LoanReportDto {
    private UUID id;
    private BigDecimal loanAmount;
    private int numberOfInstallments;
    private LocalDate createDate;
    private boolean isPaid;
}
