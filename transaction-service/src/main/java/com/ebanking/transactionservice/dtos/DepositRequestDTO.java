package com.ebanking.transactionservice.dtos;

import java.math.BigDecimal;

public record DepositRequestDTO (

        String toAccount,
        BigDecimal amount

){

}
