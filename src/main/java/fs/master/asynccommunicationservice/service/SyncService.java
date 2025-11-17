package fs.master.asynccommunicationservice.service;

import fs.master.asynccommunicationservice.exception.AsyncException;
import fs.master.asynccommunicationservice.model.SyncLog;
import fs.master.asynccommunicationservice.model.SyncRequestDTO;
import fs.master.asynccommunicationservice.repository.SyncLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;

@Service
public class SyncService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SyncLogRepository syncLogRepository;

    // URLs réelles des microservices (à adapter quand ils seront lancés)
    private String getServiceBaseUrl(String serviceId) {
        return switch (serviceId.toLowerCase()) {
            case "location" -> "http://localhost:8081";
            case "parent"   -> "http://localhost:8082";
            case "bus"      -> "http://localhost:8083";
            case "driver"   -> "http://localhost:8084";
            case "stop"     -> "http://localhost:8085";
            case "group"    -> "http://localhost:8086";
            case "student"  -> "http://localhost:8087";
            case "route"    -> "http://localhost:8088";
            default -> throw new AsyncException("Service inconnu : " + serviceId);
        };
    }

    public ResponseEntity<?> routeSyncCall(String targetService, String path, SyncRequestDTO request) {
        String baseUrl = getServiceBaseUrl(targetService);
        String targetUrl = baseUrl + "/" + path;

        ResponseEntity<String> response = restTemplate.postForEntity(
                targetUrl,
                request.getPayload(),
                String.class
        );

        // Log dans la base
        syncLogRepository.save(new SyncLog(targetService, path, request.getPayload(), LocalDateTime.now()));

        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }
}