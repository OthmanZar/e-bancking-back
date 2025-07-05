package com.ebanking.transactionservice.web;


import com.ebanking.transactionservice.dtos.DepositRequestDTO;
import com.ebanking.transactionservice.dtos.DepositResponseDTO;

import com.ebanking.transactionservice.exceptions.BankAccountNotFound;
import com.ebanking.transactionservice.services.IDepositService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/deposit")
@RequiredArgsConstructor
public class DepositController {

    private final IDepositService depositService;

    @PostMapping
    public ResponseEntity<DepositResponseDTO> deposit(@RequestBody DepositRequestDTO depositRequestDTO) throws BankAccountNotFound {
        DepositResponseDTO depositResponseDTO = depositService.createDeposit(depositRequestDTO);
        return new ResponseEntity<>(depositResponseDTO, HttpStatus.CREATED);
    }

}
