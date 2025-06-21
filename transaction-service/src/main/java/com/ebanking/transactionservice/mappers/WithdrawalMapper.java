package com.ebanking.transactionservice.mappers;

import com.ebanking.transactionservice.dtos.WithdrawalResponseDTO;
import com.ebanking.transactionservice.entities.Withdrawal;
import org.springframework.stereotype.Service;

@Service
public class WithdrawalMapper {

    public WithdrawalResponseDTO   toWithdrawalResponseDTO(Withdrawal withdrawal) {
        return new WithdrawalResponseDTO(
                withdrawal.getCardId(),
                withdrawal.getAmount(),
                withdrawal.getTransactionDate(),
                withdrawal.getStatus()

        );
    }
}
