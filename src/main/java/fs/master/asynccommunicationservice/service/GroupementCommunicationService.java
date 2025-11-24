// GroupementCommunicationService.java
package fs.master.asynccommunicationservice.service;

import fs.master.asynccommunicationservice.dto.GroupeDTO;
import fs.master.asynccommunicationservice.dto.StudentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupementCommunicationService {

    private final RestTemplate restTemplate;

    @Value("${groupement.service.url}")
    private String groupementServiceUrl; // ex: http://localhost:8000

    // Liste tous les groupes
    public List<GroupeDTO> getAllGroups() {
        String url = groupementServiceUrl + "/groups";
        GroupeDTO[] arr = restTemplate.getForObject(url, GroupeDTO[].class);
        return arr == null ? List.of() : Arrays.asList(arr);
    }

    // Détail d’un groupe avec ses élèves
    public GroupeDTO getGroupById(Long id) {
        String url = groupementServiceUrl + "/groups/" + id;
        return restTemplate.getForObject(url, GroupeDTO.class);
    }

    // Générer les groupes (à partir des élèves)
    public List<GroupeDTO> generateGroups() {
        String url = groupementServiceUrl + "/groups/generate";
        GroupeDTO[] arr = restTemplate.postForObject(url, null, GroupeDTO[].class);
        return arr == null ? List.of() : Arrays.asList(arr);
    }

    // Liste des élèves d’un groupe
    public List<StudentDTO> getStudentsByGroupId(Long groupId) {
        GroupeDTO groupe = getGroupById(groupId);
        return groupe != null ? groupe.getEleves() : List.of();
    }
}
