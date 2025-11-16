package com.shamiyana.wallet.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TransactionDTO {

    @NotNull
    @Min(value = 0 , message = "Transaction Amount Cannot Be Negative" )
    private Double amount;

    @NotNull
    private Long senderUserId;

    @NotNull
    private String senderBank;

    @NotNull
    private Long recUserId;

    @NotNull
    private String recBank;


    private String desc;
}
