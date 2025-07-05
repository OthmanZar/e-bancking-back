package com.ebanking.transactionservice.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic withdrawTopic(){

        return TopicBuilder.name("withdraw-topic").build();
    }

    @Bean
    public NewTopic transferTopic(){

        return TopicBuilder.name("transfer-topic").build();
    }

    @Bean
    public NewTopic depositTopic(){

        return TopicBuilder.name("deposit-topic").build();
    }

    @Bean
    public NewTopic transactionTopic(){

        return TopicBuilder.name("transaction-topic").build();
    }


}
