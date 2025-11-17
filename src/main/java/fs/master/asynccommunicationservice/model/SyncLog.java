package fs.master.asynccommunicationservice.model;

// Importations JPA
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class SyncLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String targetService; // Service cible
    private String path; // Chemin
    private String payload; // Contenu
    private LocalDateTime timestamp;

    public SyncLog() {}

    public SyncLog(String targetService, String path, String payload, LocalDateTime timestamp) {
        this.targetService = targetService;
        this.path = path;
        this.payload = payload;
        this.timestamp = timestamp;
    }
}