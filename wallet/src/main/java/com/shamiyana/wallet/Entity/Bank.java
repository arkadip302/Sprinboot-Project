package com.shamiyana.wallet.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "bank")
@Getter
@Setter
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long idbank;

    private String bank_name;

    private Double amount;

    private Double min_balance;

    @ManyToOne
    @JoinColumn(name = "user")
    private UserDetails user;
}
