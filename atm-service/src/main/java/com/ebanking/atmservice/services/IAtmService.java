package com.ebanking.atmservice.services;

import com.ebanking.atmservice.dtos.ATMRequestDTO;
import com.ebanking.atmservice.dtos.ATMResponseDTO;
import com.ebanking.atmservice.dtos.WithdrawalRequestDTO;
import com.ebanking.atmservice.dtos.WithdrawalResponseDTO;
import com.ebanking.atmservice.exceptions.AtmNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface IAtmService {

    ATMResponseDTO createAtm(ATMRequestDTO atmRequestDTO);

    ATMResponseDTO getAtm(String reference) throws AtmNotFoundException;

    WithdrawalResponseDTO withdrawATM(WithdrawalRequestDTO withdrawalRequestDTO) throws AtmNotFoundException, JsonProcessingException;

}
