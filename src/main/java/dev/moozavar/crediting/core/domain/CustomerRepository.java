package dev.moozavar.crediting.core.domain;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    Optional<Customer> find(UUID customerId);

    void update(Customer customer);

}
