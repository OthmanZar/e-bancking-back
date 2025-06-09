package com.ebanking.bankaccountservice.web;

import com.ebanking.bankaccountservice.exceptions.BankAccountNotFound;
import com.ebanking.bankaccountservice.services.ICurrentAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/account/current")
@RequiredArgsConstructor
public class CurrentAccountController {
    private final ICurrentAccountService currentAccountService;
    // Create a current account for a client
    @PostMapping("/create/{clientId}")
    public ResponseEntity<String> createAccount(@PathVariable Long clientId) {
        currentAccountService.createCurrentAccount(clientId);
        return ResponseEntity.ok("Current account created successfully for client ID: " + clientId);
    }

    // Get balance of a current account
    @GetMapping("/{id}/balance")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable Long id) throws BankAccountNotFound {
        BigDecimal balance = currentAccountService.getAmountOfCurrentAccount(id);
        return ResponseEntity.ok(balance);
    }

    // Delete a current account
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) throws BankAccountNotFound {
        currentAccountService.deleteCurrentAccount(id);
        return ResponseEntity.ok("Current account deleted successfully.");
    }
}
