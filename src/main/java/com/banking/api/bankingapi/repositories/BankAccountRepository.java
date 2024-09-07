package com.banking.api.bankingapi.repositories;

import com.banking.api.bankingapi.modules.bankAccount.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccount, Integer> {
    Optional<BankAccount> findByAccountNumber(String fromAccountNumber);



}