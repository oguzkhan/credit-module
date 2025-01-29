package dev.moozavar.crediting.core.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
public class Loan extends BaseModel {

    @Builder
    private Loan(UUID id, Customer customer, Money loanAmount, InterestRate interestRate, NumberOfInstallments numberOfInstallments, LocalDate createDate, boolean isPaid) {
        super(id);
        this.customer = customer;
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.numberOfInstallments = numberOfInstallments;
        this.createDate = createDate;
        this.isPaid = isPaid;
    }

    public static List<LoanInstallment> createInstallments(Loan loan) {
        List<LoanInstallment> loanInstallments = new ArrayList<>();
        Money installmentAmount = loan.calculateInstallmentAmount();
        LocalDate nextInstallmentDueDate = LocalDate.now().withDayOfMonth(1);

        for (int installmentNo = 1; installmentNo <= loan.getNumberOfInstallments().value; installmentNo++) {
            nextInstallmentDueDate = nextInstallmentDueDate.plusMonths(1);
            LoanInstallment installment = LoanInstallment.builder()
                    .id(UUID.randomUUID())
                    .loan(loan)
                    .amount(installmentAmount)
                    .paidAmount(Money.ZERO)
                    .dueDate(nextInstallmentDueDate)
                    .isPaid(false)
                    .build();
            loanInstallments.add(installment);
        }
        return loanInstallments;
    }

    @Setter
    private Customer customer;
    @Setter
    private List<LoanInstallment> loanInstallments = new ArrayList<>();
    private Money loanAmount;
    private InterestRate interestRate;
    private NumberOfInstallments numberOfInstallments;
    private LocalDate createDate;
    private boolean isPaid;

    public List<LoanInstallment> getLoanInstallments() {
        return loanInstallments.stream()
                .sorted(Comparator.comparing(LoanInstallment::getDueDate))
                .collect(Collectors.toList());
    }

    public void addInstallments(List<LoanInstallment> loanInstallments) {
        for (var loanInstallment : loanInstallments)
            loanInstallment.setLoan(this);
        this.loanInstallments.addAll(loanInstallments);
    }

    public Money calculateTotalInstallmentAmount() {
        return Money.of(loanAmount.amount().multiply(BigDecimal.valueOf(1 + interestRate.rate())));
    }

    public Money calculateInstallmentAmount() {
        return calculateTotalInstallmentAmount().divide(numberOfInstallments.value);
    }

    public void updatePaymentStatus() {
        for (LoanInstallment installment : getLoanInstallments()) {
            if (!installment.isPaid()) return;
        }
        this.isPaid = true;
    }


}
