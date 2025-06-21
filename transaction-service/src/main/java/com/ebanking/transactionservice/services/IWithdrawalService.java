package com.ebanking.transactionservice.services;

import com.ebanking.transactionservice.dtos.WithdrawalRequestDTO;
import com.ebanking.transactionservice.dtos.WithdrawalResponseDTO;
import com.ebanking.transactionservice.exceptions.BankAccountNotFound;
import org.apache.coyote.BadRequestException;

public interface IWithdrawalService {

    WithdrawalResponseDTO withdrawal(WithdrawalRequestDTO withdrawalRequestDTO) throws BadRequestException, BankAccountNotFound;

}
