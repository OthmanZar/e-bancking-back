package com.ebanking.userservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "bankAccountClient" , url = "http://localhost:9093")
public interface BankAccountClient {

    @PostMapping("/api/account/current/create/{clientId}")
    void createAccount(@PathVariable() Long clientId, @RequestBody String email);
}
