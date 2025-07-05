package com.ebanking.transactionservice.mappers;

import com.ebanking.transactionservice.dtos.DepositResponseDTO;
import com.ebanking.transactionservice.entities.Deposit;
import org.springframework.stereotype.Service;

@Service
public class DepositMapper {

    public DepositResponseDTO toDepositResponseDTO(Deposit deposit) {
        return new DepositResponseDTO(
                deposit.getTargetAccountReference(),
                deposit.getAmount(),
                deposit.getTransactionDate(),
                deposit.getStatus()
        );
    }
}
