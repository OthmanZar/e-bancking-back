package com.ebanking.bankaccountservice.web;

import com.ebanking.bankaccountservice.dtos.CurrentResponseDTO;
import com.ebanking.bankaccountservice.dtos.UpdateCurrentAccount;
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
    public ResponseEntity<String> createAccount(@PathVariable Long clientId,@RequestBody String email) {
        currentAccountService.createCurrentAccount(clientId,email);
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

    @PostMapping("/{accountNumber}")
    public ResponseEntity<CurrentResponseDTO> getCurrentAccountByAccountNumber(@PathVariable String accountNumber) throws BankAccountNotFound {
        CurrentResponseDTO currentResponseDTO = currentAccountService.getCurrentAccountByAccountNumber(accountNumber);
        return ResponseEntity.ok(currentResponseDTO);
    }

    @PostMapping("/id/{id}")
    public ResponseEntity<CurrentResponseDTO> getCurrentAccountById(@PathVariable Long id) throws BankAccountNotFound {
        CurrentResponseDTO currentResponseDTO = currentAccountService.getCurrentAccountByID(id);
        return ResponseEntity.ok(currentResponseDTO);
    }

    @PostMapping
    public ResponseEntity<CurrentResponseDTO> updateCurrentAccount(@RequestBody UpdateCurrentAccount updateCurrentAccount) throws BankAccountNotFound {
        CurrentResponseDTO currentResponseDTO =
                currentAccountService.updateCurrentAccountByAccountNumber(updateCurrentAccount.accountNumber(),updateCurrentAccount.amount());
        return ResponseEntity.ok(currentResponseDTO);
    }

    @PostMapping("/client/{id}")
    public ResponseEntity<CurrentResponseDTO> getCurrentByClientID(@PathVariable Long id) throws BankAccountNotFound {
        CurrentResponseDTO currentResponseDTO = currentAccountService.getCurrentAccountByClient_ID(id);
        return ResponseEntity.ok(currentResponseDTO);
    }

}
