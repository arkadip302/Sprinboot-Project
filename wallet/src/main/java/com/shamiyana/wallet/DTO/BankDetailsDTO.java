package com.shamiyana.wallet.DTO;


import lombok.Data;

@Data
public class BankDetailsDTO {

    private String bankName;

    private Double amount;

    private Double minBalance;
}
