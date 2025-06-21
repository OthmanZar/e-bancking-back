package com.ebanking.transactionservice.dtos;


import com.ebanking.transactionservice.enums.CardStatus;

public record UpdateCardDTO(
        Long cardID,
        CardStatus status
) {
}
