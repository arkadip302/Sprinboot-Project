package com.shamiyana.wallet.repository;

import com.shamiyana.wallet.Entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BankRepository extends JpaRepository<Bank, Long> {

    @Query("Select b From Bank b WHERE b.user.id = :userId")
    List<Bank> findByBankUserId(@Param("userId") Long userId);

    @Query("Select b From Bank b WHERE b.user.id = :userId AND b.bank_name = :bankName")
    Optional<List<Bank>> findByBankUserIdAndBankName(@Param("userId") Long userId, @Param("bankName") String bankName);
}
