package fs.master.asynccommunicationservice.controller;

// Importations nécessaires
import fs.master.asynccommunicationservice.model.SyncRequestDTO;
import fs.master.asynccommunicationservice.service.SyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sync") // Base path pour les endpoints synchrones
public class SyncController {
    @Autowired
    private SyncService syncService; // Service synchrone

    // Route un appel synchrone vers un microservice cible
    @PostMapping("/{targetService}/{path}")
    public ResponseEntity<?> routeSyncCall(
            @PathVariable String targetService, // Service cible (ex: location)
            @PathVariable String path, // Chemin (ex: student)
            @RequestBody SyncRequestDTO request // Payload
    ) {
        return syncService.routeSyncCall(targetService, path, request); // Route la requête
    }
}