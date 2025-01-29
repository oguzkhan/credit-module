package dev.moozavar.crediting.core.port;

import dev.moozavar.crediting.core.port.mapper.CustomerDto;

import java.util.*;

public interface CustomerStorage {

    Optional<CustomerDto> find(UUID customerId);

    void update(CustomerDto customerDto);

}
