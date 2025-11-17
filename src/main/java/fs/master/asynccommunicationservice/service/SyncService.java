package fs.master.asynccommunicationservice.service;

// Importations nécessaires
import fs.master.asynccommunicationservice.exception.AsyncException;
import fs.master.asynccommunicationservice.model.SyncLog;
import fs.master.asynccommunicationservice.model.SyncRequestDTO;
import fs.master.asynccommunicationservice.repository.SyncLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class SyncService {
    @Autowired
    private RestTemplate restTemplate; // Pour router les requêtes REST
    @Autowired
    private DiscoveryClient discoveryClient; // Découverte via Eureka
    @Autowired
    private SyncLogRepository syncLogRepository; // Logs synchrones

    // Route un appel synchrone
    public ResponseEntity<?> routeSyncCall(String targetService, String path, SyncRequestDTO request) {
        String targetUrl = getServiceUrl(targetService) + "/" + path; // Construit l'URL cible
        ResponseEntity<String> response = restTemplate.postForEntity(targetUrl, request.getPayload(), String.class); // Route
        syncLogRepository.save(new SyncLog(targetService, path, request.getPayload(), LocalDateTime.now())); // Logue
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody()); // Retourne réponse
    }

    // Récupère l'URL d'un service via Eureka
    private String getServiceUrl(String serviceId) {
        return discoveryClient.getInstances(serviceId).stream()
                .findFirst()
                .map(instance -> instance.getUri().toString())
                .orElseThrow(() -> new AsyncException("Service not found: " + serviceId));
    }
}