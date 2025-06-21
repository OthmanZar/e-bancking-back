package com.ebanking.bankaccountservice.kafka;

import com.ebanking.bankaccountservice.dtos.BankAccountConfirmation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationProducer {
    private final KafkaTemplate<String, BankAccountConfirmation> kafkaTemplate;

    public void sendNotification(BankAccountConfirmation request){
        log.info("Sending Notification with body <{}>",request);

        Message<BankAccountConfirmation> message = MessageBuilder.
                withPayload(request).
                setHeader(KafkaHeaders.TOPIC,"bank-account-topic")
                .build();
        kafkaTemplate.send(message);
    }

}
