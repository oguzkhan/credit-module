package dev.moozavar.crediting.core.application;

import dev.moozavar.crediting.core.application.exception.ApplicationMessages;
import dev.moozavar.crediting.core.application.mapper.*;
import dev.moozavar.crediting.core.application.exception.ApplicationException;
import dev.moozavar.crediting.core.domain.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerApplicationMapper customerApplicationMapper;

    @Transactional
    @Override
    public CreateLoanResponse createLoan(UUID customerId, CreateLoanRequest createLoanRequest) {
        var customer = getCustomer(customerId);
        Money loanAmount = Money.of(createLoanRequest.loanAmount());
        InterestRate interestRate = new InterestRate(createLoanRequest.interestRate());
        NumberOfInstallments numberOfInstallments =
                NumberOfInstallments.valueOf(createLoanRequest.numberOfInstallments());
        log.info("Creating new loan... customerId: {}", customerId);
        Loan newLoan = Customer
                .createLoan(customer, loanAmount, interestRate, numberOfInstallments);
        log.info("Updating customer for new loan... customerId: {}", customerId);
        customerRepository.update(customer);
        return customerApplicationMapper.toCreateLoanResponse(newLoan);
    }

    @Override
    public List<LoanResponse> getLoans(UUID customerId) {
        var customer = getCustomer(customerId);
        return customer.getLoans().stream()
                .map(customerApplicationMapper::toLoanResponse).toList();
    }

    @Override
    public List<LoanInstallmentResponse> getLoanInstallments(UUID customerId, UUID loanId) {
        var customer = getCustomer(customerId);
        var loan = getLoan(loanId, customer);
        return loan.getLoanInstallments().stream()
                .map(customerApplicationMapper::toLoanInstallmentResponse).toList();
    }

    @Override
    public PayLoanResponse payLoan(UUID customerId, PayLoanRequest payLoanRequest) {
        var customer = getCustomer(customerId);
        var loanToPay = getLoan(payLoanRequest.loanId(), customer);
        Money amount = Money.of(payLoanRequest.amount());
        log.info("Paying loan... customerId: {}, loanId: {}", customerId, loanToPay.getId());
        Customer.LoanPaymentResult loanPaymentResult = customer.payLoan(loanToPay, amount);
        log.info("Updating customer for paid loan... customerId: {}, loanId: {}", customerId, loanToPay.getId());
        customerRepository.update(customer);
        return customerApplicationMapper.toPayLoanResponse(loanPaymentResult);
    }

    private Customer getCustomer(UUID customerId) {
        return customerRepository.find(customerId)
                .orElseThrow(() -> new ApplicationException(ApplicationMessages.APPLICATION_CUSTOMER_NOT_FOUND));
    }

    private Loan getLoan(UUID loanId, Customer customer) {
        return customer.getLoans().stream()
                .filter(ln -> ln.getId().equals(loanId)).findFirst()
                .orElseThrow(() -> new ApplicationException(ApplicationMessages.APPLICATION_LOAN_DOES_NOT_BELONG_TO_CUSTOMER));
    }


}
