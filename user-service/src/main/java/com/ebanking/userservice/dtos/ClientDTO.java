package com.ebanking.userservice.dtos;

import lombok.Data;

@Data
public class ClientDTO {
    private Long id;
    private String reference;
    private String email;
    private String firstName;
    private String lastName;
}
