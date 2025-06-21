package com.ebanking.transactionservice.services;

import com.ebanking.transactionservice.dtos.TransferRequestDTO;
import com.ebanking.transactionservice.dtos.TransferResponseDTO;
import com.ebanking.transactionservice.exceptions.BankAccountNotFound;

public interface ITransferService {

    TransferResponseDTO transfer(TransferRequestDTO transferRequestDTO) throws BankAccountNotFound;
}
