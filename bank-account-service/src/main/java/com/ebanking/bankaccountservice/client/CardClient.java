package com.ebanking.bankaccountservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.smartcardio.Card;

@FeignClient(name = "cardClient" , url = "http://localhost:9094/api/card/")
public interface CardClient {

    @PostMapping("create/{accountID}")
    void createCard(@PathVariable long accountID,@RequestBody String email);
}
