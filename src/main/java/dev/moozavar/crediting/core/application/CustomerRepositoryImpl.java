package dev.moozavar.crediting.core.application;

import dev.moozavar.crediting.core.domain.Customer;
import dev.moozavar.crediting.core.domain.CustomerRepository;
import dev.moozavar.crediting.core.port.CustomerStorage;
import dev.moozavar.crediting.core.port.mapper.CustomerPortMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepository {
    private final CustomerStorage customerStorage;
    private final CustomerPortMapper customerPortMapper;


    @Override
    public Optional<Customer> find(UUID customerId) {
        return customerStorage.find(customerId).map(customerPortMapper::toCustomer);
    }

    @Override
    public void update(Customer customer) {
        var customerDto = customerPortMapper.toCustomerDto(customer);
        customerStorage.update(customerDto);
    }

}
