package com.ebanking.notificationservice.dtos;


import com.ebanking.notificationservice.enums.CardStatus;

import java.time.LocalDate;

public record CardConfirmation(
        String cardNumber,
        Integer cvv,
        LocalDate expiryDate,
        String status,
        String destinationEmail
){
}
