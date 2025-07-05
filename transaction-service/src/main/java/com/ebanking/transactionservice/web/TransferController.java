package com.ebanking.transactionservice.web;

import com.ebanking.transactionservice.dtos.TransferRequestDTO;
import com.ebanking.transactionservice.dtos.TransferResponseDTO;
import com.ebanking.transactionservice.exceptions.BankAccountNotFound;
import com.ebanking.transactionservice.services.ITransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transfer")
@RequiredArgsConstructor
public class TransferController {

    private final ITransferService transferService;

    @PostMapping
    public ResponseEntity<TransferResponseDTO> transfer(@RequestBody TransferRequestDTO transferRequestDTO) throws BankAccountNotFound {
        TransferResponseDTO transfer = transferService.transfer(transferRequestDTO);
        return new ResponseEntity<>(transfer, HttpStatus.CREATED);
    }


}
