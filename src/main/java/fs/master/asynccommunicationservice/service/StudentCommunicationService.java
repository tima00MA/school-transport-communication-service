package fs.master.asynccommunicationservice.service;


import fs.master.asynccommunicationservice.dto.BusDTO;
import fs.master.asynccommunicationservice.dto.StudentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
@Service
@RequiredArgsConstructor
public class StudentCommunicationService {

    private final RestTemplate restTemplate;
    private @Lazy final BusCommunicationService busService;

    @Value("${student.service.url}")
    private String studentServiceUrl;

    // 1️⃣ Liste de tous les étudiants
    public List<StudentDTO> getAllStudents() {
        ResponseEntity<StudentDTO[]> response = restTemplate.getForEntity(studentServiceUrl, StudentDTO[].class);
        return Arrays.asList(response.getBody());
    }

    // 2️⃣ Récupérer un étudiant
    public StudentDTO getStudentById(Long id) {
        return restTemplate.getForObject(studentServiceUrl + "/" + id, StudentDTO.class);
    }

    // 3️⃣ Créer un étudiant
    public StudentDTO createStudent(StudentDTO studentDTO) {
        return restTemplate.postForObject(studentServiceUrl, studentDTO, StudentDTO.class);
    }

    // 4️⃣ Mettre à jour un étudiant (notamment busId)
    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) {
        restTemplate.put(studentServiceUrl + "/" + id, studentDTO);
        return getStudentById(id);
    }

    // 5️⃣ Récupérer un étudiant enrichi avec son bus
    public StudentDTO getStudentWithBus(Long studentId) {
        StudentDTO student = getStudentById(studentId);
        if(student.getBusId() != null) {
            BusDTO bus = busService.getBusById(student.getBusId());
            student.setBus(bus);
        }
        return student;
    }

    // 6️⃣ Liste des étudiants d’un bus, optimisée
    public List<StudentDTO> getStudentsByBusId(Long busId) {
        List<StudentDTO> allStudents = getAllStudents();
        if (allStudents.stream().noneMatch(s -> busId.equals(s.getBusId()))) {
            return List.of();
        }
        BusDTO bus = busService.getBusById(busId); // un seul appel
        return allStudents.stream()
                .filter(s -> busId.equals(s.getBusId()))
                .map(s -> { s.setBus(bus); return s; })
                .toList();
    }

    // 7️⃣ Vérifier si le bus a de la place
    public boolean isBusFull(Long busId) {
        BusDTO bus = busService.getBusById(busId);
        int capacity = bus.getCapacity();
        int currentCount = getStudentsByBusId(busId).size();
        return currentCount >= capacity;
    }

    // 8️⃣ Vérifier si un étudiant est assigné à un bus
    public boolean isStudentInBus(Long studentId, Long busId) {
        StudentDTO student = getStudentById(studentId);
        return busId.equals(student.getBusId());
    }
}
