package com.ebanking.transactionservice.handler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

public enum BusinessErrorCodes {

    // Generic
    NO_CODE(0, NOT_IMPLEMENTED, "No code"),

    // Auth
    INCORRECT_CURRENT_PASSWORD(300, BAD_REQUEST, "Current password is incorrect"),
    NEW_PASSWORD_DOES_NOT_MATCH(301, BAD_REQUEST, "The new password does not match"),
    ACCOUNT_LOCKED(302, FORBIDDEN, "User account is locked"),
    ACCOUNT_DISABLED(303, FORBIDDEN, "User account is disabled"),
    BAD_CREDENTIALS(304, FORBIDDEN, "Login and / or Password is incorrect"),

    // User registration
    EMAIL_ALREADY_EXISTS(310, CONFLICT, "Email already exists"),
    USERNAME_ALREADY_EXISTS(311, CONFLICT, "Username already exists"),
    WEAK_PASSWORD(312, BAD_REQUEST, "Password does not meet complexity requirements"),
    INVALID_EMAIL_FORMAT(313, BAD_REQUEST, "Email format is invalid"),

    // Access & roles
    UNAUTHORIZED_ACCESS(320, UNAUTHORIZED, "You are not authorized to access this resource"),
    FORBIDDEN_OPERATION(321, FORBIDDEN, "You do not have permission to perform this operation"),
    ROLE_NOT_FOUND(322, NOT_FOUND, "Specified role was not found"),

    // Account management
    USER_NOT_FOUND(330, NOT_FOUND, "User not found"),
    ACCOUNT_NOT_VERIFIED(331, FORBIDDEN, "Account email is not verified"),
    TOKEN_EXPIRED(332, UNAUTHORIZED, "Token has expired"),
    INVALID_TOKEN(333, UNAUTHORIZED, "Token is invalid or malformed"),

    // Validation
    INVALID_INPUT(340, BAD_REQUEST, "Invalid input data"),
    MISSING_REQUIRED_FIELDS(341, BAD_REQUEST, "One or more required fields are missing"),

    // System
    INTERNAL_ERROR(500, INTERNAL_SERVER_ERROR, "An unexpected error occurred"),
    SERVICE_UNAVAILABLE(501, HttpStatus.SERVICE_UNAVAILABLE, "The service is temporarily unavailable"),
    DATABASE_ERROR(502, INTERNAL_SERVER_ERROR, "A database error occurred"),

    // Feign/client
    DOWNSTREAM_SERVICE_ERROR(510, BAD_GATEWAY, "Error communicating with downstream service"),
    TIMEOUT_OCCURRED(511, GATEWAY_TIMEOUT, "The request to downstream service timed out");

    @Getter
    private final int code;
    @Getter
    private final String description;
    @Getter
    private final HttpStatus httpStatus;

    BusinessErrorCodes(int code, HttpStatus status, String description) {
        this.code = code;
        this.description = description;
        this.httpStatus = status;
    }
}
