package com.ebanking.transactionservice.dtos;

import com.ebanking.transactionservice.enums.AccountStatus;

import java.math.BigDecimal;

public record CurrentResponseDTO(
        Long id,
       String accountNumber,
       BigDecimal balance,
       AccountStatus status,
        String email,
        String fullName
){
}
