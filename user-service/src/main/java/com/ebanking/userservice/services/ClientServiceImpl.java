package com.ebanking.userservice.services;

import com.ebanking.userservice.client.BankAccountClient;
import com.ebanking.userservice.dtos.ClientConfirmation;
import com.ebanking.userservice.dtos.ClientRequestDTO;
import com.ebanking.userservice.entities.Client;
import com.ebanking.userservice.exceptions.UserNotFoundException;
import com.ebanking.userservice.kafka.NotificationProducer;
import com.ebanking.userservice.mappers.ClientMapper;
import com.ebanking.userservice.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final BankAccountClient bankAccountClient;
    private final NotificationProducer notificationProducer;

    @Override
    @Transactional
    public ClientRequestDTO createClientAccount(ClientRequestDTO clientRequestDTO) {
        Client client = clientMapper.dtoToClient(clientRequestDTO);
        client.setReference(UUID.randomUUID().toString());
        client.setIsVerified(true);
        Client save = clientRepository.save(client);

        notificationProducer.sendNotification(clientMapper.clientToConfirmation(save));

        bankAccountClient.createAccount(save.getId(), save.getEmail());

        return clientMapper.clientToDTO(save);
    }

    @Override
    public ClientRequestDTO updateClientAccount(ClientRequestDTO clientRequestDTO) {

        return null;
    }

    @Override
    public void deleteClient(String reference) throws UserNotFoundException {
        Client client =  clientRepository.findByReference(reference).orElseThrow(() ->
                new UserNotFoundException("Client Not Found !!")
                );

        clientRepository.delete(client);
    }

    @Override
    public ClientRequestDTO getClientByReference(String reference) throws UserNotFoundException {
        Client client = clientRepository.findByReference(reference).orElseThrow(() ->
                new UserNotFoundException("Client Not Found !!")
        );
        return clientMapper.clientToDTO(client);
    }

    @Override
    public List<ClientRequestDTO> getAllClients() {

        List<Client> clients = clientRepository.findAll();

        if(clients.isEmpty()){
            return List.of();
        }else {
            return clients.stream().map(clientMapper::clientToDTO).toList();
        }
    }

    @Override
    public ClientRequestDTO getClientByID(Long id) throws UserNotFoundException {
        Client client = clientRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("Client Not Found !!")
        );
        return clientMapper.clientToDTO(client);
    }
}
