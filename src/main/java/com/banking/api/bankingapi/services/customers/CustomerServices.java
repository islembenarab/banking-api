package com.banking.api.bankingapi.services.customers;

import com.banking.api.bankingapi.modules.customer.Customer;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomerServices extends UserDetailsService {
    void createCustomer(Customer customer);

    Customer findCustomerByUsername(String username);
}
