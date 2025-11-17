package fs.master.asynccommunicationservice.config;

// Importations pour Eureka
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDiscoveryClient // Active la découverte via Eureka
public class EurekaConfig {
    // Configure le client Eureka pour enregistrer ce microservice
    // La configuration est gérée via application.yml
}