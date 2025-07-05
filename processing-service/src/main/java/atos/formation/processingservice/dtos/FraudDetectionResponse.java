package atos.formation.processingservice.dtos;

import java.util.List;

public record FraudDetectionResponse(
        double fraud_probability_Model,
        double fraud_probability_risk_score,
        double fraud_probability_total,
        String prediction,
        List<String> rules_triggered
) {
}
