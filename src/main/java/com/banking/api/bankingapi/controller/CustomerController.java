package com.banking.api.bankingapi.controller;


import com.banking.api.bankingapi.services.customers.CustomerServices;
import com.banking.api.bankingapi.utils.BaseController;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")
public class CustomerController extends BaseController {
    private final CustomerServices customerServices;
    // get customer info
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @GetMapping()
    ResponseEntity<?> getCustomerInfo() {
        return ResponseEntity.ok(customerServices.findCustomerByUsername(getCurrentCustomerUsername()));
    }

}
