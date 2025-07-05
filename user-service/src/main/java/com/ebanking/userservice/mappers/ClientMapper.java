package com.ebanking.userservice.mappers;

import com.ebanking.userservice.dtos.ClientConfirmation;
import com.ebanking.userservice.dtos.ClientRequestDTO;
import com.ebanking.userservice.dtos.ClientResponseDTO;
import com.ebanking.userservice.entities.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientMapper {

   public Client dtoToClient(ClientRequestDTO clientRequestDTO){
        Client client = new Client();
        client.setAddress(clientRequestDTO.address());
        client.setBirthday(clientRequestDTO.birthday());
        client.setNationalID(clientRequestDTO.nationalID());
        client.setEmail(clientRequestDTO.email());
        client.setFirstName(clientRequestDTO.firstName());
        client.setLastName(clientRequestDTO.lastName());
        client.setPhoneNumber(clientRequestDTO.phoneNumber());
        client.setSexe(clientRequestDTO.sexe());
        client.setUserType(clientRequestDTO.type());
        return client;
    }

    public ClientResponseDTO clientToDTO(Client client){

       return new ClientResponseDTO(
               client.getId(),
               client.getFirstName(),
               client.getLastName(),
               client.getEmail(),
               client.getPhoneNumber(),
               client.getSexe(),
               client.getNationalID(),
               client.getBirthday(),
               client.getAddress(),
               client.getUserType(),
               client.getImageUrl()
       );
    }

    public ClientConfirmation clientToConfirmation(Client client){

        return new ClientConfirmation(
                client.getFirstName(),
                client.getLastName(),
                client.getEmail(),
                client.getPhoneNumber(),
                client.getSexe(),
                client.getNationalID(),
                client.getBirthday(),
                client.getAddress(),
                client.getUserType()
        );
    }

}
