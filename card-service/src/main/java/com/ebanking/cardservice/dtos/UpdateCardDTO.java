package com.ebanking.cardservice.dtos;

import com.ebanking.cardservice.enums.CardStatus;


public record UpdateCardDTO(
        Long cardID,
        CardStatus status
) {
}
