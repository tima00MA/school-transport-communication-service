package fs.master.asynccommunicationservice.service;


import fs.master.asynccommunicationservice.dto.BusDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BusCommunicationService {

    private final RestTemplate restTemplate;

    @Value("${bus.service.url}")
    private String busServiceUrl;

    // Liste de tous les bus
    public List<BusDTO> getAllBuses() {
        ResponseEntity<BusDTO[]> response = restTemplate.getForEntity(busServiceUrl, BusDTO[].class);
        return Arrays.asList(response.getBody());
    }

    // Récupérer un bus par son id
    public BusDTO getBusById(Long id) {
        return restTemplate.getForObject(busServiceUrl + "/" + id, BusDTO.class);
    }
}
