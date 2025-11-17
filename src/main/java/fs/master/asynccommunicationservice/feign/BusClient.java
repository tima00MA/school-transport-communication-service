package fs.master.asynccommunicationservice.feign;



import fs.master.asynccommunicationservice.model.Bus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "bus-service", url = "${services.bus.url}")
public interface BusClient {

    @GetMapping("/api/buses")
    List<Bus> getAllBuses();

    @GetMapping("/api/buses/{id}")
    Bus getBusById(@PathVariable("id") Long id);

    @PostMapping("/api/buses")
    Bus addBus(@RequestBody Bus bus);

    @PutMapping("/api/buses/{id}")
    Bus updateBus(@PathVariable("id") Long id, @RequestBody Bus bus);

    @DeleteMapping("/api/buses/{id}")
    void deleteBus(@PathVariable("id") Long id);
}