package com.ebanking.cardservice.services;

import com.ebanking.cardservice.dtos.CardConfirmation;
import com.ebanking.cardservice.dtos.CardResponseDTO;
import com.ebanking.cardservice.dtos.UpdateCardDTO;
import com.ebanking.cardservice.entities.Card;
import com.ebanking.cardservice.enums.CardStatus;
import com.ebanking.cardservice.exception.CardNotFoundException;
import com.ebanking.cardservice.kafka.NotificationProducer;
import com.ebanking.cardservice.mappers.CardMapper;
import com.ebanking.cardservice.repositories.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements ICardService {

    private final CardRepository cardRepository;
    private final CardMapper cardMapper;
    private final NotificationProducer notificationProducer;

    @Transactional
    @Override
    public void createCard(Long currentAccountId,String email) {
        Card card = new Card();
        card.setStatus(CardStatus.ACTIVE);
        String cardNumber = generateUniqueCardNumber();
        card.setCardNumber(cardNumber);
        Integer cvv = generateUniqueCVV();
        card.setCvv(cvv);
        card.setExpiryDate(LocalDate.now().plusYears(3));
        card.setCurrentAccountId(currentAccountId);

        Card save = cardRepository.save(card);

    notificationProducer.sendNotification(new CardConfirmation
            (save.getCardNumber(),
                    save.getCvv(),
                    save.getExpiryDate(),
                    save.getStatus(),email));

    }

    private Integer generateUniqueCVV() {
        Integer cvv;
        Random random = new Random();
        do {
            cvv = random.nextInt(1000);
        } while (cardRepository.existsByCvv(cvv));

        return cvv;
    }

    private String generateUniqueCardNumber() {
        String accountNumber;
        Random random = new Random();
        String prefix = "1102";
        do {
            long randomPart = Math.abs(random.nextLong()) % 1_0000_0000_0000L;
            String randomPartStr = String.format("%012d", randomPart);
            accountNumber = prefix + randomPartStr;
            accountNumber = formatAccountNumber(accountNumber);
        } while (cardRepository.existsByCardNumber(accountNumber));

        return accountNumber;
    }
    private String formatAccountNumber(String accountNumber) {
        return accountNumber.replaceAll("(.{4})(?!$)", "$1 ");
    }

    @Transactional
    @Override
    public void updateCard(UpdateCardDTO updateCardDTO) throws CardNotFoundException {
        Card card = cardRepository.findById(updateCardDTO.cardID()).orElseThrow(() ->
                new CardNotFoundException("Card Not Found With this Number"));

        card.setStatus(updateCardDTO.status());
        cardRepository.save(card);
    }

    @Override
    public CardResponseDTO getCardByCardNumber(String cardNumber) throws CardNotFoundException {
        Card card = cardRepository.findByCardNumber(cardNumber).orElseThrow(() ->
                new CardNotFoundException("Card Not Found With this Number"));

        return cardMapper.toResponse(card);
    }
}
