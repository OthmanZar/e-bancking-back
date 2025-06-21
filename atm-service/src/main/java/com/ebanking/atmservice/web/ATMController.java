package com.ebanking.atmservice.web;

import com.ebanking.atmservice.dtos.ATMRequestDTO;
import com.ebanking.atmservice.dtos.ATMResponseDTO;
import com.ebanking.atmservice.dtos.WithdrawalRequestDTO;
import com.ebanking.atmservice.dtos.WithdrawalResponseDTO;
import com.ebanking.atmservice.entities.ATM;
import com.ebanking.atmservice.exceptions.AtmNotFoundException;
import com.ebanking.atmservice.services.IAtmService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/atm")
@RequiredArgsConstructor
public class ATMController {

    private final IAtmService atmService;

    @PostMapping
    public ResponseEntity<ATMResponseDTO> createAtm(@RequestBody ATMRequestDTO atmRequestDTO) {

        ATMResponseDTO saved = atmService.createAtm(atmRequestDTO);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{reference}")
    public ResponseEntity<ATMResponseDTO> getAtm(@PathVariable String reference) throws AtmNotFoundException {
        ATMResponseDTO atm = atmService.getAtm(reference);

        return ResponseEntity.ok(atm);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<WithdrawalResponseDTO> withdrawATM(@RequestBody WithdrawalRequestDTO withdrawalRequestDTO)
            throws AtmNotFoundException, JsonProcessingException {

        WithdrawalResponseDTO withdrawalResponseDTO = atmService.withdrawATM(withdrawalRequestDTO);

        return ResponseEntity.ok(withdrawalResponseDTO);
    }

}
