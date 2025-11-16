package com.shamiyana.wallet.repository;

import com.shamiyana.wallet.Entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDetails,Long> {

    UserDetails findByName(String name);

    UserDetails findByEmail(String email);
}
