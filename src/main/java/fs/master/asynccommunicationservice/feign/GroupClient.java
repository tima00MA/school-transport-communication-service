package fs.master.asynccommunicationservice.feign;


import fs.master.asynccommunicationservice.model.Eleve;
import fs.master.asynccommunicationservice.model.Group;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@FeignClient(name = "group-service", url = "${services.group.url}")
public interface GroupClient {

    @GetMapping("/groups")
    List<Group> getAllGroups();

    @GetMapping("/groups/{id}")
    Group getGroupById(@PathVariable("id") Long id);

    @PostMapping("/groups/generate")
    List<Group> generateGroups();

    @PutMapping("/groups/{id}")
    Group updateGroup(
            @PathVariable("id") Long id,
            @RequestParam("nom") String nom,
            @RequestParam("taille") Integer taille
    );

    @DeleteMapping("/groups/{id}")
    void deleteGroup(@PathVariable("id") Long id);
}
