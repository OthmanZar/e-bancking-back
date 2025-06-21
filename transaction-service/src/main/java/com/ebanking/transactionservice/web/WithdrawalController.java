package com.ebanking.transactionservice.web;

import com.ebanking.transactionservice.dtos.WithdrawalRequestDTO;
import com.ebanking.transactionservice.dtos.WithdrawalResponseDTO;
import com.ebanking.transactionservice.exceptions.BankAccountNotFound;
import com.ebanking.transactionservice.services.IWithdrawalService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/withdraw")
public class WithdrawalController {
    private final IWithdrawalService withdrawalService;

    @PostMapping
    public ResponseEntity<WithdrawalResponseDTO> withdraw(@RequestBody @Validated WithdrawalRequestDTO withdrawalRequestDTO) throws BadRequestException, BankAccountNotFound {
        System.out.println(withdrawalRequestDTO);


        WithdrawalResponseDTO withdrawal = withdrawalService.withdrawal(withdrawalRequestDTO);
        return new ResponseEntity<>(withdrawal, HttpStatus.OK);
    }
}
