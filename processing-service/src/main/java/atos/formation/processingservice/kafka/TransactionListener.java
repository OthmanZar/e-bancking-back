package atos.formation.processingservice.kafka;

import atos.formation.processingservice.dtos.FraudDetectionResponse;
import atos.formation.processingservice.dtos.TransactionNotification;
import atos.formation.processingservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionListener {

   private final TransactionService transactionService;

    @KafkaListener(topics = "transaction-topic")
    public void handleTransaction(TransactionNotification transactionNotification) {
        log.info("Received transaction payload: {}", transactionNotification);
        FraudDetectionResponse fraudDetectionResponse = transactionService.sendTransactionToFraudSystem(transactionNotification);
        System.out.println(fraudDetectionResponse.prediction());
    }
}
