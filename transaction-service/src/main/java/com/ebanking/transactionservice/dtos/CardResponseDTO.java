package com.ebanking.transactionservice.dtos;


import com.ebanking.transactionservice.enums.CardStatus;

import java.time.LocalDate;

public record CardResponseDTO(
        Long id,
        String cardNumber,
        Integer cvv,
        LocalDate expiryDate,
        CardStatus status,
        Long currentAccountId
) {
}
