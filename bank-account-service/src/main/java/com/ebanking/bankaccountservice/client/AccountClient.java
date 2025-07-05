package com.ebanking.bankaccountservice.client;


import com.ebanking.bankaccountservice.dtos.ClientResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@FeignClient(name = "accountClient" , url = "http://localhost:9091/api/clients")
public interface AccountClient {

    @GetMapping("id/{id}")
    Optional<ClientResponseDTO> getClientByID(@PathVariable Long id);
}
