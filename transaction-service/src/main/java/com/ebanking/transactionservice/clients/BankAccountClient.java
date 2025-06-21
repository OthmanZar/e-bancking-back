package com.ebanking.transactionservice.clients;

import com.ebanking.transactionservice.dtos.CurrentResponseDTO;
import com.ebanking.transactionservice.dtos.UpdateCurrentAccount;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@FeignClient(name = "bankAccountClient" , url = "http://localhost:9093/api/account")
public interface BankAccountClient {

    @PostMapping("/current/{accountNumber}")
    Optional<CurrentResponseDTO> getCurrentAccount(@PathVariable String accountNumber);

    @PostMapping("/current/id/{id}")
    Optional<CurrentResponseDTO> getCurrentAccountByID(@PathVariable Long id);

    @PostMapping("/current")
    Optional<CurrentResponseDTO> updateCurrentAccount(@RequestBody UpdateCurrentAccount updateCurrentAccount);

}
