package fs.master.asynccommunicationservice.service;

import fs.master.asynccommunicationservice.dto.UserDTO;
import fs.master.asynccommunicationservice.dto.UserCheckDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthCommunicationService {

    private final RestTemplate restTemplate;

    @Value("${auth.service.url}") // ex: http://localhost:8090/auth
    private String authServiceUrl;

    /**
     * Récupérer un utilisateur par son ID (synchrone)
     */
    public UserDTO getUserById(String userId) {
        // URL corrigée : correspond exactement au controller Auth
        String url = String.format("%s/user/%s", authServiceUrl, userId);
        return restTemplate.getForObject(url, UserDTO.class);
    }

    /**
     * Vérifier si un utilisateur est actif et son rôle
     */
    public UserCheckDTO checkUser(String userId) {
        // URL corrigée : correspond au mapping /auth/user/{userId}/check
        String url = String.format("%s/user/%s/check", authServiceUrl, userId);
        return restTemplate.getForObject(url, UserCheckDTO.class);
    }

    /**
     * Récupérer tous les utilisateurs
     */
    public List<UserDTO> getAllUsers() {
        // URL corrigée : correspond au mapping /auth/users
        String url = String.format("%s/users", authServiceUrl);
        UserDTO[] arr = restTemplate.getForObject(url, UserDTO[].class);
        return arr == null ? List.of() : Arrays.asList(arr);
    }
}
