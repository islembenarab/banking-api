package com.banking.api.bankingapi.modules.transactions;


import com.banking.api.bankingapi.modules.bankAccount.BankAccount;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "from_account")
    private BankAccount fromAccount;

    @ManyToOne
    @JoinColumn(name = "to_account")
    private BankAccount toAccount;

    @Column(name = "amount", nullable = false)
    private java.math.BigDecimal amount;


    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private java.sql.Timestamp createdAt;

}
