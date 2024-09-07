package com.banking.api.bankingapi.services.bankAccount;

import com.banking.api.bankingapi.dtos.CreateBankRequest;
import com.banking.api.bankingapi.exceptions.ApiException;
import com.banking.api.bankingapi.modules.customer.Customer;

import com.banking.api.bankingapi.modules.bankAccount.BankAccount;
import com.banking.api.bankingapi.modules.transactions.Transactions;
import com.banking.api.bankingapi.repositories.BankAccountRepository;
import com.banking.api.bankingapi.repositories.TransactionsRepository;
import com.banking.api.bankingapi.services.customers.CustomerServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final CustomerServices customerServices;
    private final TransactionsRepository transactionsRepository;

    @Override
    public void createAccount(CreateBankRequest createBankRequest) {
        Customer customer = customerServices.findCustomerByUsername(createBankRequest.customerUsername());
        BankAccount bankAccount =BankAccount.builder()
                .id(customer.getId())
                .accountNumber(UUID.randomUUID().toString())
                .accountType(createBankRequest.accountType())
                .balance(createBankRequest.initialDeposit())
                .customer(customer)
                .build();
        bankAccountRepository.save(bankAccount);
    }
    @Transactional
    @Override
    public void transferMoney(String whoMakeTransfer,String fromAccountNumber, String toAccountNumber, BigDecimal amount) {
        BankAccount fromAccount = bankAccountRepository.findByAccountNumber(fromAccountNumber).orElseThrow(()->new ApiException("Account not found", HttpStatus.BAD_REQUEST));
        BankAccount toAccount = bankAccountRepository.findByAccountNumber(toAccountNumber).orElseThrow(()->new ApiException("Account not found", HttpStatus.BAD_REQUEST));
        if(!fromAccount.getCustomer().getUsername().equals(whoMakeTransfer)){
            throw new ApiException("You are not allowed to make this transfer", HttpStatus.BAD_REQUEST);
        }
        if (fromAccount.getCustomer().getUsername().equals(toAccount.getCustomer().getUsername())){
            throw new ApiException("You can't transfer money to yourself", HttpStatus.BAD_REQUEST);
        }
        if(fromAccount.getBalance().compareTo(amount) < 0){
            throw new ApiException("Insufficient balance", HttpStatus.BAD_REQUEST);
        }
        try{
            decreaseBalance(fromAccount, amount);
            increaseBalance(toAccount, amount);
            Transactions transactions = Transactions.builder()
                    .fromAccount(fromAccount)
                    .toAccount(toAccount)
                    .amount(amount)
                    .build();

            transactionsRepository.save(transactions);
        }catch (Exception e){
            throw new ApiException("error in transfer money "+e.getMessage(),  HttpStatus.BAD_REQUEST);
        }

    }

    private void increaseBalance(BankAccount toAccount, BigDecimal amount) {
        toAccount.setBalance(toAccount.getBalance().add(amount));
        bankAccountRepository.save(toAccount);
    }

    private void decreaseBalance(BankAccount fromAccount, BigDecimal amount) {
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        bankAccountRepository.save(fromAccount);
    }
}
