package com.shamiyana.wallet.controller;

import com.shamiyana.wallet.Entity.UserDetails;
import com.shamiyana.wallet.repository.UserRepository;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserDetails(@PathVariable Integer userId){
        Optional<UserDetails> resp = userRepository.findById(Long.valueOf(userId));
        if(resp.isPresent()){
            return ResponseEntity.notFound().build();
        }else{
            return  ResponseEntity.ok(resp.get());
        }
    }

    @PostMapping("/user")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserDetails userDetails){
        if( userRepository.findByEmail(userDetails.getEmail())!=null){
            return ResponseEntity.badRequest().body("Email Id Already Registered");
        }
        userRepository.save(userDetails);
        return ResponseEntity.ok(userDetails);
    }
}
