package com.shamiyana.wallet.repository;

import com.shamiyana.wallet.Entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {

}
