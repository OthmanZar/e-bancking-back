package com.ebanking.cardservice.dtos;

import com.ebanking.cardservice.enums.CardStatus;

import java.time.LocalDate;

public record CardConfirmation (
        String cardNumber,
        Integer cvv,
        LocalDate expiryDate,
        CardStatus status,
        String destinationEmail
){
}
