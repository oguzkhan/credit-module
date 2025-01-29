package dev.moozavar.crediting.core.port.mapper;

import dev.moozavar.crediting.core.domain.*;
import org.mapstruct.*;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring", imports = {PersonName.class, PersonSurname.class, Money.class, InterestRate.class, NumberOfInstallments.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerPortMapper {

    @Mapping(target = "name", expression = "java(new PersonName(customerDto.getName()))")
    @Mapping(target = "surname", expression = "java(new PersonSurname(customerDto.getSurname()))")
    @Mapping(target = "creditLimit", expression = "java(Money.of(customerDto.getCreditLimit()))")
    @Mapping(target = "usedCreditLimit", expression = "java(Money.of(customerDto.getUsedCreditLimit()))")
    Customer toCustomer(CustomerDto customerDto);

    @AfterMapping
    default void aftertoCustomer(CustomerDto customerDto, @MappingTarget Customer customer) {
        var loans = customerDto.getLoanDtos().stream()
                .map(this::toLoan).collect(Collectors.toList());
        customer.addLoans(loans);
    }

    @Mapping(target = "name", expression = "java(customer.getName().name())")
    @Mapping(target = "surname", expression = "java(customer.getSurname().surname())")
    @Mapping(target = "creditLimit", expression = "java(customer.getCreditLimit().amount())")
    @Mapping(target = "usedCreditLimit", expression = "java(customer.getUsedCreditLimit().amount())")
    CustomerDto toCustomerDto(Customer customer);

    @AfterMapping
    default void aftertoCustomerDto(@MappingTarget CustomerDto customerDto, Customer customer) {
        var loanDtos = customer.getLoans().stream()
                .map(this::toLoanDto).collect(Collectors.toList());
        customerDto.setLoanDtos(loanDtos);
    }

    @Mapping(target = "loanAmount", expression = "java(Money.of(loanDto.getLoanAmount()))")
    @Mapping(target = "interestRate", expression = "java(new InterestRate(loanDto.getInterestRate()))")
    @Mapping(target = "numberOfInstallments", expression = "java(NumberOfInstallments.valueOf(loanDto.getNumberOfInstallments()))")
    Loan toLoan(LoanDto loanDto);

    @AfterMapping
    default void aftertoLoan(LoanDto loanDto, @MappingTarget Loan loan) {
        var installments = loanDto.getInstallmentDtos().stream()
                .map(this::toLoanInstallment).collect(Collectors.toList());
        loan.addInstallments(installments);
    }

    @Mapping(target = "customerId", source = "loan.customer.id")
    @Mapping(target = "loanAmount", expression = "java(loan.getLoanAmount().amount())")
    @Mapping(target = "interestRate", expression = "java(loan.getInterestRate().rate())")
    @Mapping(target = "numberOfInstallments", expression = "java(loan.getNumberOfInstallments().value)")
    LoanDto toLoanDto(Loan loan);

    @AfterMapping
    default void aftertoLoanDto(@MappingTarget LoanDto loanDto, Loan loan) {
        var installmentDtos = loan.getLoanInstallments().stream()
                .map(this::toLoanInstallmentDto).collect(Collectors.toList());
        loanDto.setInstallmentDtos(installmentDtos);
    }

    @Mapping(target = "amount", expression = "java(Money.of(loanInstallmentDto.getAmount()))")
    @Mapping(target = "paidAmount", expression = "java(Money.of(loanInstallmentDto.getPaidAmount()))")
    LoanInstallment toLoanInstallment(LoanInstallmentDto loanInstallmentDto);

    @Mapping(target = "loanId", source = "loanInstallment.loan.id")
    @Mapping(target = "amount", expression = "java(loanInstallment.getAmount().amount())")
    @Mapping(target = "paidAmount", expression = "java(loanInstallment.getPaidAmount().amount())")
    LoanInstallmentDto toLoanInstallmentDto(LoanInstallment loanInstallment);


}
