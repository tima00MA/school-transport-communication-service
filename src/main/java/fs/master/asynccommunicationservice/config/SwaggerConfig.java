package fs.master.asynccommunicationservice.config;

// Importations pour OpenAPI (Swagger)
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    // Configuration de Swagger pour documenter l'API REST
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info() // Définit les métadonnées de l'API
                        .title("Async Communication Service API") // Titre de l'API
                        .version("1.0") // Version
                        .description("API for synchronous and asynchronous communication")); // Description
    }
}