package com.ebanking.userservice.dtos;

import lombok.Data;

import java.util.List;

@Data
public class AuthResponseDTO {
    private String accessToken;
    private String refreshToken;
    private Long expiresIn; // expiration timestamp or seconds
    private ClientDTO client;
    private List<String> roles;
}
