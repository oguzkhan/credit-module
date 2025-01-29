package dev.moozavar.crediting.adapter.restapi;

import dev.moozavar.crediting.core.application.CustomerService;
import dev.moozavar.crediting.core.application.mapper.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/{customerId}/loans")
    public ResponseEntity<CreateLoanResponse> createLoan(@PathVariable UUID customerId,
                                                         @RequestBody CreateLoanRequest createLoanRequest) {
        log.info("Creating customer loan customerId: {}, createLoanRequest: {}", customerId, createLoanRequest);
        return ResponseEntity.ok(customerService.createLoan(customerId, createLoanRequest));
    }

    @GetMapping("/{customerId}/loans")
    public ResponseEntity<List<LoanResponse>> getLoans(@PathVariable UUID customerId) {
        log.info("Getting customer loans customerId: {}", customerId);
        return ResponseEntity.ok(customerService.getLoans(customerId));
    }

    @GetMapping("/{customerId}/loans/{loanId}/installments")
    public ResponseEntity<List<LoanInstallmentResponse>> getLoanInstallments(@PathVariable UUID customerId, @PathVariable UUID loanId) {
        log.info("Getting customer loans customerId: {}, loanId: {}", customerId, loanId);
        return ResponseEntity.ok(customerService.getLoanInstallments(customerId, loanId));
    }

    @PostMapping("/{customerId}/pay-loan")
    public ResponseEntity<PayLoanResponse> payLoan(@PathVariable UUID customerId,
                                                   @RequestBody PayLoanRequest payLoanRequest) {
        log.info("Paying customer loan customerId: {}, payLoanRequest: {}", customerId, payLoanRequest);
        return ResponseEntity.ok(customerService.payLoan(customerId, payLoanRequest));
    }
}


