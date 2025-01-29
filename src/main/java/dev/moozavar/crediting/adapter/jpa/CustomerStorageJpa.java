package dev.moozavar.crediting.adapter.jpa;

import dev.moozavar.crediting.core.port.CustomerStorage;
import dev.moozavar.crediting.core.port.mapper.CustomerDto;
import dev.moozavar.crediting.core.port.mapper.LoanDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomerStorageJpa implements CustomerStorage {

    private final CustomerDataAccessor customerDataAccessor;
    private final CustomerDataMapper customerDataMapper;

    @Override
    public Optional<CustomerDto> find(UUID customerId) {
        var customer = customerDataAccessor.findById(customerId);
        return customer.map(customerDataMapper::toCustomerDto);
    }

    @Override
    public void update(CustomerDto customerDto) {
        CustomerEntity customerEntity = customerDataAccessor
                .findById(customerDto.getId()).orElseThrow();
        customerEntity.setUsedCreditLimit(customerDto.getUsedCreditLimit());
        for (LoanDto loanDto : customerDto.getLoanDtos()) {
            var srcInst = loanDto.getInstallmentDtos().stream()
                    .map(customerDataMapper::toLoanInstallmentEntity)
                    .collect(Collectors.toList());
            LoanEntity srcLoan = customerDataMapper.toLoanEntity(loanDto);
            srcLoan.addLoanInstallments(srcInst);
            var targetLoan = customerEntity.getLoans().stream()
                    .filter(ln -> ln.equals(srcLoan)).findFirst();
            if (targetLoan.isEmpty())
                customerEntity.addLoan(srcLoan);
            else
                targetLoan.get().updateWith(srcLoan);
        }
        customerDataAccessor.save(customerEntity);
    }

}
