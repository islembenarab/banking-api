package com.banking.api.bankingapi.repositories;

import com.banking.api.bankingapi.modules.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByUsernameOrEmail(String username, String email);
}