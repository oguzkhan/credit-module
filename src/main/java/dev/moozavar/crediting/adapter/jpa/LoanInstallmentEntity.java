package dev.moozavar.crediting.adapter.jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "loan_installment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanInstallmentEntity extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "loan_id")
    private LoanEntity loan;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "paid_amount")
    private BigDecimal paidAmount;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Column(name = "is_paid")
    private boolean isPaid;


}
