package com.ebanking.cardservice.kafka;

import com.ebanking.cardservice.dtos.CardConfirmation;
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
    private final KafkaTemplate<String, CardConfirmation> kafkaTemplate;

    public void sendNotification(CardConfirmation request){
        log.info("Sending Notification with body <{}>",request);

        Message<CardConfirmation> message = MessageBuilder.
                withPayload(request).
                setHeader(KafkaHeaders.TOPIC,"card-topic")
                .build();
        kafkaTemplate.send(message);
    }

}
