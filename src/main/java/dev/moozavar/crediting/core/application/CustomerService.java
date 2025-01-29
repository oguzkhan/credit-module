package dev.moozavar.crediting.core.application;

import dev.moozavar.crediting.core.application.mapper.*;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    List<LoanResponse> getLoans(UUID customerId);

    CreateLoanResponse createLoan(UUID customerId, CreateLoanRequest createLoanRequest);

    List<LoanInstallmentResponse> getLoanInstallments(UUID customerId, UUID loanId);

    PayLoanResponse payLoan(UUID customerId, PayLoanRequest payLoanRequest);
}
