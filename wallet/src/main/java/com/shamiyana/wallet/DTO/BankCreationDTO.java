package com.shamiyana.wallet.DTO;

import com.shamiyana.wallet.Entity.UserDetails;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class BankCreationDTO {


    private String bank_name;

    private Double amount;

    private Double min_balance;

    private Long userId;
}
