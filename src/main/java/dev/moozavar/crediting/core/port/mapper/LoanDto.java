package dev.moozavar.crediting.core.port.mapper;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class LoanDto {

    private UUID id;
    private UUID customerId;
    private BigDecimal loanAmount;
    private Double interestRate;
    private int numberOfInstallments;
    private LocalDate createDate;
    private boolean isPaid;

    @Setter
    private List<LoanInstallmentDto> installmentDtos;

}
