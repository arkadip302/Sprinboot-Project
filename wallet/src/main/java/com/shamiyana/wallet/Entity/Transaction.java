package com.shamiyana.wallet.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "`transaction`")
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")       // DB column
    private Long transactionId;

    @Column(name = "`desc`")
    private String desc;

    @ManyToOne(optional = false)
    @JoinColumn(name = "rec_bank", referencedColumnName = "idbank")
    private Bank rec_bank;

    @ManyToOne(optional = false)
    @JoinColumn(name = "send_bank", referencedColumnName = "idbank")
    private Bank send_bank;

    private Boolean status;

    private Double amount;
}
