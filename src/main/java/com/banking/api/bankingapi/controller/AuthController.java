package com.banking.api.bankingapi.controller;

import com.banking.api.bankingapi.dtos.LoginRequest;
import com.banking.api.bankingapi.modules.customer.Customer;
import com.banking.api.bankingapi.modules.customer.Role;
import com.banking.api.bankingapi.services.auth.AuthService;
import com.banking.api.bankingapi.services.customers.CustomerServices;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.jose4j.lang.JoseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@PermitAll
public class AuthController {
    private final CustomerServices customerServices;
    private final AuthService authService;

    @PostMapping("/register-customer")
    public ResponseEntity<?> register(@RequestBody Customer customer) {
        customer.setRole(Role.ROLE_CUSTOMER.name());
        customerServices.createCustomer(customer);
        return new ResponseEntity<>("Customer created successfully", HttpStatus.CREATED);
    }
    @PostMapping("/register-admin")
    public ResponseEntity<?> registerAdmin(@RequestBody Customer customer) {
        customer.setRole(Role.ROLE_ADMIN.name());
        customerServices.createCustomer(customer);
        return new ResponseEntity<>("Admin created successfully", HttpStatus.CREATED);
    }
    @PostMapping("/register-manager")
    public ResponseEntity<?> registerManager(@RequestBody Customer customer) {
        customer.setRole(Role.ROLE_MANAGER.name());
        customerServices.createCustomer(customer);
        return new ResponseEntity<>("Admin created successfully", HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws JoseException {

        return new ResponseEntity<>(authService.login(loginRequest), HttpStatus.OK);
    }
}
