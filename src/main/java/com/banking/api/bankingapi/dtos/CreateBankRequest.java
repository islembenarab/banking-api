package com.banking.api.bankingapi.dtos;

import com.banking.api.bankingapi.modules.bankAccount.AccountType;

import java.math.BigDecimal;

public record CreateBankRequest (String customerUsername, AccountType accountType, BigDecimal initialDeposit) {
}
