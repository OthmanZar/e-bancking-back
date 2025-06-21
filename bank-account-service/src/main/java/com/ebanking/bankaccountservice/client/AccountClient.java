package com.ebanking.bankaccountservice.client;

import com.ebanking.bankaccountservice.dtos.ClientRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@FeignClient(name = "accountClient" , url = "http://localhost:9091/api/clients")
public interface AccountClient {

    @GetMapping("id/{id}")
    Optional<ClientRequestDTO> getClientByID(@PathVariable Long id);
}
