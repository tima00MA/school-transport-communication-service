package fs.master.asynccommunicationservice.event;

import lombok.Data;

@Data
public class GenericMessage {
    private String queue; // Nom de la file
    private String payload; // Contenu générique
}