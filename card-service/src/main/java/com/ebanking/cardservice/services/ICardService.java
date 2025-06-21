package com.ebanking.cardservice.services;

import com.ebanking.cardservice.dtos.CardResponseDTO;
import com.ebanking.cardservice.dtos.UpdateCardDTO;
import com.ebanking.cardservice.entities.Card;
import com.ebanking.cardservice.exception.CardNotFoundException;

public interface ICardService {

    void createCard(Long currentAccountId,String email);

    void updateCard(UpdateCardDTO updateCardDTO) throws CardNotFoundException;

    CardResponseDTO getCardByCardNumber(String cardNumber) throws CardNotFoundException;
}
