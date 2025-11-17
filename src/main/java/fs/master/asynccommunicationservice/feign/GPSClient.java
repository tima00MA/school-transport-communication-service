package fs.master.asynccommunicationservice.feign;

import fs.master.asynccommunicationservice.model.GPSLocation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "gps-service", url = "${services.gps.url}")
public interface GPSClient {

    @GetMapping("/locations/{entity_id}")
    GPSLocation getLocation(@PathVariable("entity_id") Long id);

    @PostMapping("/locations/student")
    GPSLocation updateStudentLocation(@RequestBody GPSLocation location);

    @PostMapping("/locations/bus")
    GPSLocation updateBusLocation(@RequestBody GPSLocation location);
}
