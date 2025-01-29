package dev.moozavar.crediting.core.application.mapper;

import dev.moozavar.crediting.core.domain.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", imports = {Money.class, InterestRate.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerApplicationMapper {


    CreateLoanResponse toCreateLoanResponse(Loan newLoan);

    @Mapping(target = "customerId", source = "loan.customer.id")
    @Mapping(target = "loanAmount", source = "loan.loanAmount.amount")
    @Mapping(target = "interestRate", source = "loan.interestRate.rate")
    @Mapping(target = "numberOfInstallments", source = "loan.numberOfInstallments.value")
    LoanResponse toLoanResponse(Loan loan);

    @Mapping(target = "loanId", source = "loanInstallment.loan.id")
    @Mapping(target = "amount", source = "loanInstallment.amount.amount")
    @Mapping(target = "paidAmount", source = "loanInstallment.paidAmount.amount")
    LoanInstallmentResponse toLoanInstallmentResponse(LoanInstallment loanInstallment);

    @Mapping(target = "totalAmountSpent", source = "loanPaymentResult.totalAmountSpent.amount")
    PayLoanResponse toPayLoanResponse(Customer.LoanPaymentResult loanPaymentResult);
}
