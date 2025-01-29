package dev.moozavar.crediting.core.domain;

import dev.moozavar.crediting.core.domain.exception.DomainException;
import dev.moozavar.crediting.core.domain.exception.DomainMessages;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Customer extends BaseModel {

    @Builder
    private Customer(UUID id, PersonName name, PersonSurname surname, Money creditLimit, Money usedCreditLimit) {
        super(id);
        this.name = name;
        this.surname = surname;
        this.creditLimit = creditLimit;
        this.usedCreditLimit = usedCreditLimit;
    }

    @Setter
    private List<Loan> loans = new ArrayList<>();

    private PersonName name;

    private PersonSurname surname;

    private Money creditLimit;

    private Money usedCreditLimit;

    public static Loan createLoan(Customer customer, Money loanAmount, InterestRate interestRate, NumberOfInstallments numberOfInstallments) {
        customer.checkCreditLimit(loanAmount);
        Loan newLoan = Loan.builder()
                .id(UUID.randomUUID())
                .customer(customer)
                .loanAmount(loanAmount)
                .interestRate(interestRate)
                .numberOfInstallments(numberOfInstallments)
                .createDate(LocalDate.now())
                .isPaid(false)
                .build();

        newLoan.addInstallments(Loan.createInstallments(newLoan));
        customer.loans.add(newLoan);
        customer.usedCreditLimit = customer.usedCreditLimit.add(loanAmount);
        return newLoan;
    }

    public void addLoans(List<Loan> customerLoans) {
        for (Loan loan : customerLoans)
            loan.setCustomer(this);
        this.loans.addAll(customerLoans);
    }

    public void addLoan(Loan loan) {
        loan.setCustomer(this);
        this.loans.add(loan);
    }

    public LoanPaymentResult payLoan(Loan loanToPay, Money amount) {
        Money amountRemaining = Money.of(amount.amount());
        LocalDate paymentDate = LocalDate.now();
        boolean allInstallmentsPaid = false;
        int numberOfPaidInstallments = 0;
        Money totalAmountSpent = Money.ZERO;
        for (LoanInstallment installment : loanToPay.getLoanInstallments()) {
            if (!installment.isPaid()) {
                amountRemaining = installment.pay(paymentDate, amountRemaining);
                if (installment.isPaid()) numberOfPaidInstallments++;
            }
        }
        totalAmountSpent = amount.subtract(amountRemaining);
        if (loanToPay.isPaid()) allInstallmentsPaid = true;
        return new LoanPaymentResult(this.getId(), loanToPay.getId(),
                numberOfPaidInstallments, totalAmountSpent, allInstallmentsPaid);
    }

    public void checkCreditLimit(Money creditAmount) {
        if (creditAmount.greaterThan(Money.of(this.creditLimit.amount().subtract(this.usedCreditLimit.amount()))))
            throw new DomainException(DomainMessages.DOMAIN_CUSTOMER_NOT_ENOUGH_CREDIT_LIMIT);
    }

    public record LoanPaymentResult(UUID customerId,
                                    UUID loanId,
                                    int numberOfPaidInstallments,
                                    Money totalAmountSpent,
                                    boolean allInstallmentsPaid) {
    }
}
