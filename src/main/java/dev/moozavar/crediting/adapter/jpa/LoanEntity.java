package dev.moozavar.crediting.adapter.jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "loan")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanEntity extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LoanInstallmentEntity> loanInstallments = new ArrayList<>();

    @Column(name = "loan_amount")
    private BigDecimal loanAmount;

    @Column(name = "interest_rate")
    private Double interestRate;

    @Column(name = "number_of_installments")
    private int numberOfInstallments;

    @Column(name = "create_date", updatable = false)
    private LocalDate createDate;

    @Column(name = "is_paid")
    private boolean isPaid;

    public void addLoanInstallments(List<LoanInstallmentEntity> loanInstallmentsToAdd) {
        for (LoanInstallmentEntity loanInstallment : loanInstallmentsToAdd)
            loanInstallment.setLoan(this);
        this.loanInstallments.addAll(loanInstallmentsToAdd);
    }

    public void updateWith(LoanEntity loanEntity) {
        this.isPaid = loanEntity.isPaid;
        for (var inst : loanInstallments) {
            var source = loanEntity.getLoanInstallments().stream()
                    .filter(installment -> installment.equals(inst))
                    .findFirst().orElseThrow();
            inst.setPaid(source.isPaid());
            inst.setPaidAmount(source.getPaidAmount());
            inst.setPaymentDate(source.getPaymentDate());
        }
    }
}
