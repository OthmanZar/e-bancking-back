package com.ebanking.cardservice.handler;


import com.ebanking.cardservice.exception.CardNotFoundException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exp) {
        Set<String> errors = new HashSet<>();
        exp.getBindingResult().getAllErrors()
                .forEach(error -> {
                    //var fieldName = ((FieldError) error).getField();
                    var errorMessage = error.getDefaultMessage();
                    errors.add(errorMessage);
                });

        return ResponseEntity
                .status(BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .validationErrors(errors)
                                .build()
                );
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ExceptionResponse> handleFeignException(FeignException exp) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            String responseBody = exp.contentUTF8();

            // Try to parse as your custom ExceptionResponse
            try {
                ExceptionResponse decoded = mapper.readValue(responseBody, ExceptionResponse.class);
                return ResponseEntity
                        .status(exp.status())
                        .body(decoded);
            } catch (Exception inner) {
                // Try to parse as Spring Boot default error response
                JsonNode jsonNode = mapper.readTree(responseBody);
                if (jsonNode.has("status") && jsonNode.has("error")) {
                    return ResponseEntity
                            .status(jsonNode.get("status").asInt())
                            .body(ExceptionResponse.builder()
                                    .businessErrorCode(jsonNode.get("status").asInt())
                                    .businessErrorDescription("Downstream error: " + jsonNode.get("error").asText())
                                    .error("Path: " + jsonNode.path("path").asText())
                                    .build());
                }

                // Unknown structure fallback
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ExceptionResponse.builder()
                                .businessErrorDescription("Unrecognized downstream error")
                                .error("Raw: " + responseBody)
                                .build());
            }

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ExceptionResponse.builder()
                            .businessErrorDescription("Internal error, please contact the admin")
                            .error("Exception parsing Feign response: " + e.getMessage())
                            .build());
        }
    }

    @ExceptionHandler(CardNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleUserException(CardNotFoundException exp) {

        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorDescription("Card Exception")
                                .error(exp.getMessage())
                                .build()
                );
    }


}
