package dev.moozavar.crediting.core.port.mapper;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class CustomerDto {

    private UUID id;

    private String name;

    private String surname;

    private BigDecimal creditLimit;

    private BigDecimal usedCreditLimit;

    @Setter
    private List<LoanDto> loanDtos = new ArrayList<>();

}
