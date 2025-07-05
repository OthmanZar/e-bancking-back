package com.ebanking.userservice.services;

import com.ebanking.userservice.client.BankAccountClient;
import com.ebanking.userservice.dtos.ClientConfirmation;
import com.ebanking.userservice.dtos.ClientRequestDTO;
import com.ebanking.userservice.dtos.ClientResponseDTO;
import com.ebanking.userservice.entities.Client;
import com.ebanking.userservice.exceptions.UserNotFoundException;
import com.ebanking.userservice.kafka.NotificationProducer;
import com.ebanking.userservice.mappers.ClientMapper;
import com.ebanking.userservice.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    public ClientResponseDTO createClientAccount(ClientRequestDTO clientRequestDTO) {

        String filePath = saveFile(clientRequestDTO.image());

        Client client = clientMapper.dtoToClient(clientRequestDTO);
        client.setReference(UUID.randomUUID().toString());
        client.setIsVerified(true);
        client.setImageUrl(filePath);
        Client save = clientRepository.save(client);

        notificationProducer.sendNotification(clientMapper.clientToConfirmation(save));

        bankAccountClient.createAccount(save.getId(), save.getEmail());

        return clientMapper.clientToDTO(save);
    }
    private String saveFile(MultipartFile file) {
        try {
            String uploadDir = "uploads/";

            // Extract the original file extension (e.g., ".jpg", ".png")
            String originalFilename = file.getOriginalFilename();
            String extension = "";

            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }

            // Generate unique filename with the original extension
            String fileName = UUID.randomUUID().toString() + extension;

            // Ensure the upload directory exists
            Path filePath = Paths.get(uploadDir, fileName);
            Files.createDirectories(filePath.getParent());

            // Write the file to disk
            Files.write(filePath, file.getBytes());

            return fileName; // Store in DB if needed
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
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
    public ClientResponseDTO getClientByReference(String reference) throws UserNotFoundException {
        Client client = clientRepository.findByReference(reference).orElseThrow(() ->
                new UserNotFoundException("Client Not Found !!")
        );
        return clientMapper.clientToDTO(client);
    }

    @Override
    public List<ClientResponseDTO> getAllClients() {

        List<Client> clients = clientRepository.findAll();

        if(clients.isEmpty()){
            return List.of();
        }else {
            return clients.stream().map(clientMapper::clientToDTO).toList();
        }
    }

    @Override
    public ClientResponseDTO getClientByID(Long id) throws UserNotFoundException {
        Client client = clientRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("Client Not Found !!")
        );
        return clientMapper.clientToDTO(client);
    }
}
