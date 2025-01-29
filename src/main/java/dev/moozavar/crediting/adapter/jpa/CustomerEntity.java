package dev.moozavar.crediting.adapter.jpa;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEntity extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LoanEntity> loans = new ArrayList<>();

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "credit_limit")
    private BigDecimal creditLimit;

    @Column(name = "used_credit_limit")
    private BigDecimal usedCreditLimit;


    public void addLoans(List<LoanEntity> loans) {
        for (LoanEntity loan : loans)
            loan.setCustomer(this);
        this.loans.addAll(loans);
    }

    public void addLoan(LoanEntity loan) {
        loan.setCustomer(this);
        this.loans.add(loan);
    }
}
