package com.ebanking.userservice.services;

import com.ebanking.userservice.dtos.AuthResponseDTO;
import com.ebanking.userservice.dtos.ClientDTO;
import com.ebanking.userservice.dtos.ClientRequestDTO;
import com.ebanking.userservice.dtos.ClientResponseDTO;
import com.ebanking.userservice.entities.Client;
import com.ebanking.userservice.entities.RegisterDTO;
import com.ebanking.userservice.repositories.ClientRepository;
import com.ebanking.userservice.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final ClientRepository clientRepository;
    private final ClientServiceImpl clientService;
    @Value("${keycloak.url}")
    private String keycloakUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    @Transactional
    public AuthResponseDTO register(RegisterDTO dto , MultipartFile file) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // 1. Save user locally
//        Client user = new Client();
//        BeanUtils.copyProperties(dto, user);
//        user.setReference(UUID.randomUUID().toString());
//        clientRepository.save(user);

        // 2. Create user in Keycloak
        createKeycloakUser(dto);

        ClientResponseDTO clientAccount = clientService.createClientAccount(new ClientRequestDTO(

                dto.getFirstName(),
                dto.getLastName(),
                dto.getEmail(),
                dto.getPhoneNumber(),
                dto.getSexe(),
                dto.getNationalID(),
                dto.getBirthday(),
                dto.getAddress(),
                dto.getUserType(),
                file

        ));


        // 3. Login to get full auth details (access token, refresh token, roles, etc.)
        AuthResponseDTO authResponse = loginWithDetails(dto.getEmail(), dto.getPassword());

        // 4. Prepare client DTO (optional, if not already included in login response)
        ClientDTO clientDTO = new ClientDTO();
        BeanUtils.copyProperties(clientAccount, clientDTO);
        clientDTO.setReference(authResponse.getClient().getReference());

        // 5. Update the client info in authResponse (overwrite if needed)
        authResponse.setClient(clientDTO);

        // 6. Return full response
        return authResponse;
    }
    public Client getClientByEmail(String email) {
        return clientRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Client not found"));
    }
    private void createKeycloakUser(RegisterDTO dto) {
        String token = obtainAdminAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> userPayload = new LinkedHashMap<>();
        userPayload.put("username", dto.getEmail());
        userPayload.put("email", dto.getEmail());
        userPayload.put("enabled", true);
        userPayload.put("firstName", dto.getFirstName());
        userPayload.put("lastName", dto.getLastName());

        Map<String, Object> credentials = new HashMap<>();
        credentials.put("type", "password");
        credentials.put("value", dto.getPassword());
        credentials.put("temporary", false);

        userPayload.put("credentials", List.of(credentials));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(userPayload, headers);

        String url = keycloakUrl + "/admin/realms/" + realm + "/users";

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to create Keycloak user: " + response.getBody());
        }
    }

    private String obtainAdminAccessToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "client_credentials");
        form.add("client_id", clientId);
        form.add("client_secret", clientSecret);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                keycloakUrl + "/realms/" + realm + "/protocol/openid-connect/token",
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<>() {}
        );

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new RuntimeException("Failed to obtain Keycloak admin access token");
        }

        Object token = response.getBody().get("access_token");
        if (token == null) {
            throw new RuntimeException("No access_token found in Keycloak response");
        }

        return token.toString();
    }

    public AuthResponseDTO loginWithDetails(String username, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "password");
        form.add("client_id", clientId);
        form.add("client_secret", clientSecret);
        form.add("username", username);
        form.add("password", password);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                keycloakUrl + "/realms/" + realm + "/protocol/openid-connect/token",
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<>() {}
        );

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new RuntimeException("Failed to login and obtain access token");
        }

        Map<String, Object> body = response.getBody();

        String accessToken = (String) body.get("access_token");
        String refreshToken = (String) body.get("refresh_token");
        Integer expiresIn = (Integer) body.get("expires_in"); // typically seconds till expiry

        // Extract roles from the access token JWT (decode JWT)
        List<String> roles = extractRolesFromToken(accessToken);

        // Fetch client info from DB
        Client client = getClientByEmail(username);
        ClientDTO clientDTO = new ClientDTO();
        BeanUtils.copyProperties(client, clientDTO);

        AuthResponseDTO authResponseDTO = new AuthResponseDTO();
        authResponseDTO.setAccessToken(accessToken);
        authResponseDTO.setRefreshToken(refreshToken);
        authResponseDTO.setExpiresIn(expiresIn != null ? expiresIn.longValue() : null);
        authResponseDTO.setClient(clientDTO);
        authResponseDTO.setRoles(roles);

        return authResponseDTO;
    }

    private List<String> extractRolesFromToken(String jwtToken) {
        try {
            String[] parts = jwtToken.split("\\.");
            if (parts.length < 2) return Collections.emptyList();

            String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> claims = mapper.readValue(payload, Map.class);

            Map<String, Object> resourceAccess = (Map<String, Object>) claims.get("resource_access");
            if (resourceAccess == null) return Collections.emptyList();

            Map<String, Object> clientResource = (Map<String, Object>) resourceAccess.get(clientId);
            if (clientResource == null) return Collections.emptyList();

            List<String> roles = (List<String>) clientResource.get("roles");
            return roles != null ? roles : Collections.emptyList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
