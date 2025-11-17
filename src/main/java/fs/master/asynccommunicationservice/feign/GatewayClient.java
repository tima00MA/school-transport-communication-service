package fs.master.asynccommunicationservice.feign;


import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "gateway", url = "${services.gateway.url}")
public interface GatewayClient {
    // endpoints to interact with API gateway if required
}

