package com.shamiyana.wallet.controller;

import com.shamiyana.wallet.DTO.BankCreationDTO;
import com.shamiyana.wallet.DTO.BankDetailsDTO;
import com.shamiyana.wallet.Entity.Bank;
import com.shamiyana.wallet.Entity.UserDetails;
import com.shamiyana.wallet.repository.BankRepository;
import com.shamiyana.wallet.repository.UserRepository;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class BalanceController {

    @Autowired
    BankRepository bankRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/getAccountDetails/{userId}")
    public ResponseEntity<?> getAccountDetails(@PathVariable String userId ){
        List<Bank> optionalBank = bankRepository.findByBankUserId(Long.valueOf(userId));
        List<BankDetailsDTO> bankDetailsDTOS = new ArrayList<>();

        optionalBank.forEach(e->{
            BankDetailsDTO bankDetailsDTO = new BankDetailsDTO();
            bankDetailsDTO.setBankName(e.getBank_name());
            bankDetailsDTO.setAmount(e.getAmount());
            bankDetailsDTO.setMinBalance(e.getMin_balance());
            bankDetailsDTOS.add(bankDetailsDTO);
        });
        return  ResponseEntity.ok(bankDetailsDTOS);
    }

    @GetMapping("/getTotalBalance/{userId}")
    public ResponseEntity<?> getTotalBalance(@PathVariable String userId ){
        List<Bank> optionalBank = bankRepository.findByBankUserId(Long.valueOf(userId));
        return ResponseEntity.ok(optionalBank.stream().mapToDouble(Bank::getAmount).sum());
    }

    @PostMapping("/newBank")
    public ResponseEntity<?> onboardNewBankForUser(@RequestBody BankCreationDTO bank){
        Optional<List<Bank>> bankAlreadyPresent = bankRepository.findByBankUserIdAndBankName(bank.getUserId(),bank.getBank_name());
        if(bankAlreadyPresent.isPresent() && !bankAlreadyPresent.get().isEmpty()){
            return ResponseEntity.badRequest().body("Already Account Present");
        }
        Optional<UserDetails> userDetails = userRepository.findById(bank.getUserId());
        if(userDetails.isEmpty()){
            return ResponseEntity.badRequest().body("User Id Not Valid");
        }

        Bank bank1 = new Bank();
        BeanUtils.copyProperties(bank,bank1);
        bank1.setUser(userDetails.get());
        bankRepository.save(bank1);
        return ResponseEntity.ok(bank);
    }

    @GetMapping("/allBanks")
    public ResponseEntity<?> getAllBanks(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "2") Integer pagesize , @RequestParam(defaultValue = "idbank",required = false) String sortBy){
        return  ResponseEntity.ok(bankRepository.findAll(PageRequest.of(page,pagesize,org.springframework.data.domain.Sort.by(sortBy))));
    }


}
