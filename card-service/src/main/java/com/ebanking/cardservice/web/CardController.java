package com.ebanking.cardservice.web;

import com.ebanking.cardservice.dtos.CardResponseDTO;
import com.ebanking.cardservice.dtos.UpdateCardDTO;
import com.ebanking.cardservice.exception.CardNotFoundException;
import com.ebanking.cardservice.repositories.CardRepository;
import com.ebanking.cardservice.services.ICardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/card/")
public class CardController {

    private final ICardService cardService;

    @PostMapping("create/{accountID}")
    public ResponseEntity<String> create(@PathVariable("accountID") long accountID,@RequestBody String email) {
        cardService.createCard(accountID,email);
        return ResponseEntity.ok("Card created successfully for account ID: " + accountID);

    }

    @PostMapping("{card-number}")
    public ResponseEntity<CardResponseDTO> getCardByCardNumber(@PathVariable("card-number") String cardNumber) throws CardNotFoundException {
        CardResponseDTO cardByCardNumber = cardService.getCardByCardNumber(cardNumber);
        return ResponseEntity.ok(cardByCardNumber);

    }

    @PostMapping("update")
    public ResponseEntity<String> updateCard(@RequestBody UpdateCardDTO updateCardDTO) throws CardNotFoundException {
        cardService.updateCard(updateCardDTO);
        return ResponseEntity.ok("Card Updated successfully");

    }



}
