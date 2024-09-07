package com.banking.api.bankingapi.services.bankAccount;

import com.banking.api.bankingapi.dtos.CreateBankRequest;

import java.math.BigDecimal;

public interface BankAccountService {
    void createAccount(CreateBankRequest createBankRequest);

    void transferMoney(String username,String fromAccountNumber, String toAccountNumber, BigDecimal amount);
}
