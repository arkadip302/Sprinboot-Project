package com.shamiyana.order.client;

import com.shamiyana.order.dto.TransactionDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@FeignClient(name="userbank-service",path = "/api/v1")
public interface UserBankClient {

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserDetails(@PathVariable Integer userId);

    @GetMapping("/getAccountDetails/{userId}")
    public ResponseEntity<?> getAccountDetails(@PathVariable String userId);

    @PostMapping("/transaction")
    public String transaction(@RequestBody @Valid TransactionDTO transactionDTO);
}