package com.ebanking.transactionservice.services;

import com.ebanking.transactionservice.dtos.DepositRequestDTO;
import com.ebanking.transactionservice.dtos.DepositResponseDTO;
import com.ebanking.transactionservice.exceptions.BankAccountNotFound;

public interface IDepositService {

        DepositResponseDTO createDeposit(DepositRequestDTO depositRequestDTO) throws BankAccountNotFound;

}
