package fs.master.asynccommunicationservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import fs.master.asynccommunicationservice.model.TokenValidationResponse;

@FeignClient(name = "auth-service", url = "${services.auth.url}")
public interface AuthClient {

    @PostMapping("/auth/validate")
    TokenValidationResponse validateToken(@RequestHeader("Authorization") String token);
}
