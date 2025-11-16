package com.shamiyana.wallet.controller;

import com.shamiyana.wallet.DTO.TransactionDTO;
import com.shamiyana.wallet.Entity.Bank;
import com.shamiyana.wallet.Entity.Transaction;
import com.shamiyana.wallet.repository.BankRepository;
import com.shamiyana.wallet.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class TransactionController {

    @Autowired
    BankRepository bankRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @PostMapping("/transaction")
    @Transactional
    public ResponseEntity<?> transaction(@RequestBody @Valid TransactionDTO transactionDTO){
        Optional<List<Bank>> recBankDetails = bankRepository.findByBankUserIdAndBankName(transactionDTO.getRecUserId(), transactionDTO.getRecBank());
        Optional<List<Bank>> sendBankDetails = bankRepository.findByBankUserIdAndBankName(transactionDTO.getSenderUserId(), transactionDTO.getSenderBank());

        if(recBankDetails.isPresent() && sendBankDetails.isPresent()){
            Bank recBank = recBankDetails.get().getFirst();
            Bank sendBank =   sendBankDetails.get().getFirst();
            if(Objects.equals(recBank.getIdbank(), sendBank.getIdbank())){
                return ResponseEntity.badRequest().body("Bad Request");
            }
            Transaction transaction = new Transaction();
            if(sendBank.getAmount() - transactionDTO.getAmount() > sendBank.getMin_balance()){
                sendBank.setAmount(sendBank.getAmount()-transactionDTO.getAmount());
                recBank.setAmount(recBank.getAmount()+transactionDTO.getAmount());
                bankRepository.save(recBank);
                bankRepository.save(sendBank);
                transaction.setSend_bank(sendBank);
                transaction.setRec_bank(recBank);
                transaction.setAmount(transactionDTO.getAmount());
                transaction.setStatus(Boolean.TRUE);
                transaction.setDesc(transactionDTO.getDesc());
                transaction = transactionRepository.save(transaction);
                return ResponseEntity.ok("Transaction Successful "+transaction.getTransactionId());
            }else{
                transaction.setSend_bank(sendBank);
                transaction.setRec_bank(recBank);
                transaction.setAmount(transactionDTO.getAmount());
                transaction.setStatus(Boolean.FALSE);
                transaction.setDesc(transactionDTO.getDesc());
                transactionRepository.save(transaction);
                return ResponseEntity.ok("Transaction Failed Due To Low Amount");
            }
        }else{
            return ResponseEntity.badRequest().body("Bad Request");
        }
    }

    @GetMapping("/getAllTransaction")
    public ResponseEntity<?> getAllTransaction(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer pagesize , @RequestParam(defaultValue = "transaction_id",required = false) String sortBy){
        return ResponseEntity.ok(transactionRepository.findAll(PageRequest.of(page, pagesize,org.springframework.data.domain.Sort.by(sortBy))));
    }
}
