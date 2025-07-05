package com.ebanking.bankaccountservice.web;

import com.ebanking.bankaccountservice.dtos.BankAccounts;
import com.ebanking.bankaccountservice.dtos.CurrentResponseDTO;
import com.ebanking.bankaccountservice.exceptions.BankAccountNotFound;
import com.ebanking.bankaccountservice.services.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class BankAccountController {
    private final BankAccountService bankAccountService;
    @PostMapping("/{id_client}")
    public ResponseEntity<List<BankAccounts>> getAllBankAccountsByClientID(@PathVariable Long id_client)  {
        List<BankAccounts> allBankAccounts = bankAccountService.getAllBankAccounts(id_client);
        return ResponseEntity.ok(allBankAccounts);
    }
}
