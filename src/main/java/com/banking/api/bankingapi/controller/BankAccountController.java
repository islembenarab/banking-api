package com.banking.api.bankingapi.controller;

import com.banking.api.bankingapi.dtos.CreateBankRequest;
import com.banking.api.bankingapi.services.bankAccount.BankAccountService;
import com.banking.api.bankingapi.utils.BaseController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bank-account")
public class BankAccountController extends BaseController {
    private final BankAccountService bankAccountService;
    // create bank account
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PostMapping("/create-account")
    public ResponseEntity<?> createBankAccount(@RequestBody CreateBankRequest createBankRequest) {
        // create bank account
        bankAccountService.createAccount(createBankRequest);
        return new ResponseEntity<>("Bank account created successfully", HttpStatus.CREATED);
    }

    // transfer money
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PostMapping("/transfer-money")
    public ResponseEntity<?> transferMoney(@RequestParam String fromAccountNumber, @RequestParam String toAccountNumber, @RequestParam BigDecimal amount) {
        // transfer money
        bankAccountService.transferMoney(getCurrentCustomerUsername(),fromAccountNumber, toAccountNumber, amount);
        return new ResponseEntity<>("Money transferred successfully", HttpStatus.OK);
    }
}
