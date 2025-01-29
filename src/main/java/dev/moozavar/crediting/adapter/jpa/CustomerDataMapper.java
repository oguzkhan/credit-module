package dev.moozavar.crediting.adapter.jpa;

import dev.moozavar.crediting.core.port.mapper.CustomerDto;
import dev.moozavar.crediting.core.port.mapper.LoanDto;
import dev.moozavar.crediting.core.port.mapper.LoanInstallmentDto;
import org.mapstruct.*;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerDataMapper {

    CustomerDto toCustomerDto(CustomerEntity customerEntity);

    @AfterMapping
    default void aftertoCustomerDto(@MappingTarget CustomerDto customerDto, CustomerEntity customerEntity) {
        var loanDtos = customerEntity.getLoans().stream()
                .map(this::toLoanDto).collect(Collectors.toList());
        customerDto.setLoanDtos(loanDtos);
    }

    @Mapping(target = "customerId", source = "loanEntity.customer.id")
    LoanDto toLoanDto(LoanEntity loanEntity);

    @AfterMapping
    default void aftertoLoanDto(@MappingTarget LoanDto loanDto, LoanEntity loanEntity) {
        var installmentDtos = loanEntity.getLoanInstallments().stream()
                .map(this::toLoanInstallmentDto).collect(Collectors.toList());
        loanDto.setInstallmentDtos(installmentDtos);
    }

    @Mapping(target = "loanId", source = "loanInstallmentEntity.loan.id")
    LoanInstallmentDto toLoanInstallmentDto(LoanInstallmentEntity loanInstallmentEntity);

    LoanEntity toLoanEntity(LoanDto loanDto);

    LoanInstallmentEntity toLoanInstallmentEntity(LoanInstallmentDto loanInstallmentDto);


}
