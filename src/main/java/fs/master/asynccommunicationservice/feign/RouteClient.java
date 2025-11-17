package fs.master.asynccommunicationservice.feign;


import fs.master.asynccommunicationservice.model.Route;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "route-service", url = "${services.route.url}")
public interface RouteClient {

    @GetMapping("/routes/optimal")
    List<Route> getOptimalRoutes();

    @GetMapping("/routes/eta/{student_id}")
    String getETA(@PathVariable("student_id") Long studentId);

    @PostMapping("/routes/generate")
    List<Route> generateRoutes();
}
