package fs.master.asynccommunicationservice.repository;

// Importations JPA
import fs.master.asynccommunicationservice.model.SyncLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SyncLogRepository extends JpaRepository<SyncLog, Long> {
    // Trouve les logs par service cible
    List<SyncLog> findByTargetService(String targetService);
}