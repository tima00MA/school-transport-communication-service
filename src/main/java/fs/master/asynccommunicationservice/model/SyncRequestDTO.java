package fs.master.asynccommunicationservice.model;

import lombok.Data;

@Data
public class SyncRequestDTO {
    private String payload; // Contenu de la requête synchrone
}