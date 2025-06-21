package com.ebanking.transactionservice.clients;

import com.ebanking.transactionservice.dtos.CardResponseDTO;
import com.ebanking.transactionservice.dtos.UpdateCardDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@FeignClient(name = "cardClient" , url = "http://localhost:9094/api/card/")
public interface CardClient {

    @PostMapping("{card-number}")
    Optional<CardResponseDTO> getCardByCardNumber(@PathVariable("card-number") String cardNumber);

    @PostMapping("update")
    void updateCard(@RequestBody UpdateCardDTO updateCardDTO);
}
