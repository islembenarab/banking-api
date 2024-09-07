package com.banking.api.bankingapi.repositories;

import com.banking.api.bankingapi.modules.transactions.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionsRepository extends JpaRepository<Transactions, UUID> {
    List<Transactions> findAllByFromAccount_Customer_Username(String fromAccount_customer_username);
}