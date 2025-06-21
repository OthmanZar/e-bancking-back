package com.ebanking.cardservice.mappers;

import com.ebanking.cardservice.dtos.CardResponseDTO;
import com.ebanking.cardservice.entities.Card;
import org.springframework.stereotype.Service;

@Service
public class CardMapper {

    public CardResponseDTO toResponse(Card card) {
        return new CardResponseDTO(
                card.getId(),
                card.getCardNumber(),
                card.getCvv(),
                card.getExpiryDate(),
                card.getStatus(),
                card.getCurrentAccountId()
        );
    }
}
