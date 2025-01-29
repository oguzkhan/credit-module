package dev.moozavar.crediting.adapter.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerDataAccessor extends JpaRepository<CustomerEntity, UUID> {
}
