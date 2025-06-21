package com.ebanking.cardservice.dtos;

import com.ebanking.cardservice.enums.CardStatus;

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
