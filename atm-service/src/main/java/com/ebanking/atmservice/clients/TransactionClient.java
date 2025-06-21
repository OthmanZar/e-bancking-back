package com.ebanking.atmservice.clients;

import com.ebanking.atmservice.dtos.WithdrawalRequestDTO;
import com.ebanking.atmservice.dtos.WithdrawalResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@FeignClient(name = "transactionClient" , url = "http://localhost:9095/api/withdraw")
public interface TransactionClient {

    @PostMapping
    Optional<WithdrawalResponseDTO> withdraw(@RequestBody WithdrawalRequestDTO withdrawalRequestDTO);


}
