package com.ebanking.transactionservice.services;

import com.ebanking.transactionservice.dtos.TransactionDTO;
import com.ebanking.transactionservice.dtos.TransactionNotification;
import com.ebanking.transactionservice.entities.Deposit;
import com.ebanking.transactionservice.entities.Transaction;
import com.ebanking.transactionservice.entities.Transfer;
import com.ebanking.transactionservice.entities.Withdrawal;
import com.ebanking.transactionservice.enums.TransactionType;
import com.ebanking.transactionservice.kafka.NotificationProducer;
import com.ebanking.transactionservice.repositories.DepositRepository;
import com.ebanking.transactionservice.repositories.TransactionRepository;
import com.ebanking.transactionservice.repositories.TransferRepository;
import com.ebanking.transactionservice.repositories.WithdrawalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final NotificationProducer notificationProducer;
    private final TransactionRepository transactionRepository;
    private final TransferRepository transferRepository;
    private final WithdrawalRepository withdrawalRepository;
    private final DepositRepository depositRepository;
    public void sendTransactionToProcessing(TransactionNotification notification) {
        notificationProducer.sendNotificationTransaction(notification);
    }

    public List<TransactionDTO> getAllTransactions(Long reference) {
        List<TransactionDTO> transactionDTOS = new ArrayList<>();

        // Transfers
        List<Transfer> transfers = transferRepository.findAllBySourceAccountReferenceOrTargetAccountReference(reference, reference);
        transfers.forEach(transfer -> transactionDTOS.add(new TransactionDTO(
                transfer.getReference(),
                transfer.getAmount(),
                transfer.getTransactionDate(),
                transfer.getTargetAccountReference(),
                transfer.getSourceAccountReference(),
                null,
                TransactionType.TRANSFER
        )));

        // Withdrawals
        List<Withdrawal> withdrawals = withdrawalRepository.findWithdrawalBySourceAccountReference(reference);
        withdrawals.forEach(withdrawal -> transactionDTOS.add(new TransactionDTO(
                withdrawal.getReference(),
                withdrawal.getAmount(),
                withdrawal.getTransactionDate(),
                null,
                withdrawal.getSourceAccountReference(),
                withdrawal.getReferenceATM(),
                TransactionType.WITHDRAW
        )));

        List<Deposit> deposits = depositRepository.findAllBySourceAccountReferenceOrTargetAccountReference(reference,reference);

        deposits.forEach(deposit -> transactionDTOS.add(new TransactionDTO(
                deposit.getReference(),
                deposit.getAmount(),
                deposit.getTransactionDate(),
                deposit.getTargetAccountReference(),
                deposit.getSourceAccountReference(),
                null,
                TransactionType.DEPOSIT
        )));

        transactionDTOS.sort(Comparator.comparing(TransactionDTO::transactionDate).reversed());
        return transactionDTOS;
    }

}
