package com.banking.api.bankingapi.modules.customer;

import com.banking.api.bankingapi.modules.bankAccount.BankAccount;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity(name = "costumers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.INTEGER)
    private Integer id;

    @Column(name = "name", nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String name;

    @Column(name = "email", nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String email;

    // username
    @Column(name = "username", nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String username;

    @Column(name = "password", nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String password;

    // role
    @Column(name = "role", nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String role;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private java.sql.Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private java.sql.Timestamp updatedAt;

    @OneToMany(mappedBy = "customer", cascade = {CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private List<BankAccount> bankAccounts = new ArrayList<>();

// TODO: Add more fields to the Customer class

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
