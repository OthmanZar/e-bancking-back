package com.ebanking.userservice.web;

import com.ebanking.userservice.dtos.AuthResponseDTO;
import com.ebanking.userservice.dtos.ClientDTO;
import com.ebanking.userservice.entities.Client;
import com.ebanking.userservice.entities.RegisterDTO;
import com.ebanking.userservice.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@RestController
@RequestMapping("/api/clients/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public AuthResponseDTO register(
            @RequestPart("dto") RegisterDTO dto,
            @RequestPart("file") MultipartFile file
    ) {
        return authService.register(dto, file);
    }

    @PostMapping("/login")
    public AuthResponseDTO login(@RequestParam String username, @RequestParam String password) {
        return authService.loginWithDetails(username, password);
    }
    private final String uploadDir = "uploads";
    @GetMapping("/image/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            // Try to determine content type
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

