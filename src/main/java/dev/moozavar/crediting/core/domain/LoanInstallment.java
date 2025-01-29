package dev.moozavar.crediting.core.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
public class LoanInstallment extends BaseModel {

    @Builder
    public LoanInstallment(UUID id, Loan loan, Money amount, Money paidAmount, LocalDate dueDate, LocalDate paymentDate, boolean isPaid) {
        super(id);
        this.loan = loan;
        this.amount = amount;
        this.paidAmount = paidAmount;
        this.dueDate = dueDate;
        this.paymentDate = paymentDate;
        this.isPaid = isPaid;
    }

    @Setter
    private Loan loan;

    private Money amount;

    private Money paidAmount;

    private LocalDate dueDate;

    private LocalDate paymentDate;

    private boolean isPaid;

    public boolean checkInstallmentPayable(Money paymentAmount) {
        return (checkDueDateValidForPayment()
                && checkPaymentAmountValidForPayment(paymentAmount));
    }

    public boolean checkDueDateValidForPayment() {
        LocalDate currentPeriod = LocalDate.now().withDayOfMonth(1);
        return dueDate.isBefore(currentPeriod.plusMonths(CreditConstants.MAX_PAYABLE_MONTHS));
    }

    public boolean checkPaymentAmountValidForPayment(Money paymentAmount) {
        return this.amount.lessThanOrEqual(paymentAmount);
    }

    public Money pay(LocalDate paymentDate, Money paymentAmount) {
        if (!checkInstallmentPayable(paymentAmount))
            return paymentAmount;
        this.paymentDate = paymentDate;
        this.paidAmount = Money.of(amount.amount());
        this.isPaid = true;
        loan.updatePaymentStatus();
        return paymentAmount.subtract(this.getAmount());
    }
}
