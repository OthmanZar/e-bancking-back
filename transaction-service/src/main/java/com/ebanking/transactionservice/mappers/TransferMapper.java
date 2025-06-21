package com.ebanking.transactionservice.mappers;

import com.ebanking.transactionservice.dtos.TransferResponseDTO;
import com.ebanking.transactionservice.entities.Transfer;
import org.springframework.stereotype.Service;

@Service
public class TransferMapper {

    public TransferResponseDTO toTransactionResponse(Transfer transaction) {
        return new TransferResponseDTO(
                transaction.getSourceAccountReference(),
                transaction.getTargetAccountReference(),
                transaction.getAmount(),
                transaction.getReason(),
                transaction.getTransactionDate(),
                transaction.getStatus()
        );
    }

}
