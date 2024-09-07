package com.banking.api.bankingapi.controller;

import com.banking.api.bankingapi.repositories.TransactionsRepository;
import com.banking.api.bankingapi.utils.BaseController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transactions")
public class TransactionsController extends BaseController {
    private final TransactionsRepository transactionsRepository;

    @GetMapping
    public ResponseEntity<?> getTransactions() {
        return new ResponseEntity<>(transactionsRepository.findAllByFromAccount_Customer_Username(getCurrentCustomerUsername()), org.springframework.http.HttpStatus.OK);
    }
}
