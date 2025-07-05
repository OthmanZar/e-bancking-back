package com.ebanking.transactionservice.web;


import com.ebanking.transactionservice.dtos.TransactionDTO;
import com.ebanking.transactionservice.exceptions.BankAccountNotFound;
import com.ebanking.transactionservice.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("all/{id}")
    public ResponseEntity<List<TransactionDTO>> transfer(@PathVariable("id") Long id) throws BankAccountNotFound {
        List<TransactionDTO> transfers = transactionService.getAllTransactions(id);
        return new ResponseEntity<>(transfers, HttpStatus.CREATED);
    }
}
