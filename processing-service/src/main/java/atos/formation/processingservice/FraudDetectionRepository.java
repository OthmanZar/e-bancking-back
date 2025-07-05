package atos.formation.processingservice;

import atos.formation.processingservice.entities.FraudTransactionDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface FraudDetectionRepository extends ElasticsearchRepository<FraudTransactionDocument, String> {
}
