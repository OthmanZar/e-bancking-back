package atos.formation.processingservice.service;

import atos.formation.processingservice.FraudDetectionRepository;
import atos.formation.processingservice.client.FraudDetectionClient;
import atos.formation.processingservice.dtos.FraudDetectionResponse;
import atos.formation.processingservice.dtos.TransactionNotification;
import atos.formation.processingservice.entities.FraudTransactionDocument;
import atos.formation.processingservice.utils.IpCountryResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionService {

    private final IpCountryResolver ipCountryResolver;
    private final FraudDetectionClient fraudDetectionClient;
    private final FraudDetectionRepository fraudDetectionRepository;
    public FraudDetectionResponse sendTransactionToFraudSystem(TransactionNotification notification) {

        String country = ipCountryResolver.getCountry(notification.ip_address());

        TransactionNotification transactionNotification = new TransactionNotification(
                notification.origin_user_id(),
                notification.destination_user_id(),
                notification.transaction_sequence_id(),
                notification.amount(),
                notification.origin_old_balance(),
                notification.origin_new_balance(),
                notification.dest_old_balance(),
                notification.dest_new_balance(),
                notification.avg_transaction_amount(),
                notification.time_since_last_tx(),
                country,
                notification.channel(),
                notification.transaction_type(),
                notification.timestamp(),
                notification.ip_address()
        );
        FraudDetectionResponse fraudDetectionResponse = fraudDetectionClient.detectFraud(transactionNotification);
        boolean isFraud = fraudDetectionResponse.prediction().contains("HIGH RISK") || fraudDetectionResponse.prediction().contains("MEDIUM RISK");
        saveTransactionToElasticsearch(transactionNotification,isFraud);
        return fraudDetectionResponse;

    }

    public void saveTransactionToElasticsearch(TransactionNotification tx, boolean isFraud) {
        FraudTransactionDocument document = FraudTransactionDocument.builder()
                .id(UUID.randomUUID().toString())
                .originUserId(tx.origin_user_id())
                .destinationUserId(tx.destination_user_id())
                .transactionSequenceId(tx.transaction_sequence_id())
                .amount(tx.amount())
                .originOldBalance(tx.origin_old_balance())
                .originNewBalance(tx.origin_new_balance())
                .destOldBalance(tx.dest_old_balance())
                .destNewBalance(tx.dest_new_balance())
                .avgTransactionAmount(tx.avg_transaction_amount())
                .timeSinceLastTx(tx.time_since_last_tx())
                .country(tx.country())
                .channel(tx.channel())
                .transactionType(tx.transaction_type())
                .timestamp(tx.timestamp())
                .ipAddress(tx.ip_address())
                .isFraud(isFraud)
                .build();

        fraudDetectionRepository.save(document);
    }
}
