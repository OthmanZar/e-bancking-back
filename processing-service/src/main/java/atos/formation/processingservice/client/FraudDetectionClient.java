package atos.formation.processingservice.client;

import atos.formation.processingservice.dtos.FraudDetectionResponse;
import atos.formation.processingservice.dtos.TransactionNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class FraudDetectionClient {
    private final RestTemplate restTemplate;
    private @Value("${fraud.api.url}") String fraudApiUrl;



    public FraudDetectionResponse detectFraud(TransactionNotification notification) {
        return restTemplate.postForObject(
                fraudApiUrl,
                notification,
                FraudDetectionResponse.class
        );
    }
}
