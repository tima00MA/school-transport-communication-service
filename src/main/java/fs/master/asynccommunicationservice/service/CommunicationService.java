package fs.master.asynccommunicationservice.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import fs.master.asynccommunicationservice.model.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommunicationService {

    private final RestTemplate restTemplate;

    // URLs des microservices
    private final String STUDENT_URL = "http://localhost:8082/api/students";
    private final String BUS_URL = "http://localhost:8081/api/buses";
    private final String GPS_URL = "http://localhost:8002/locations";
    private final String NOTIFICATION_URL = "http://localhost:8001/notifications";
    private final String ROUTE_URL = "http://localhost:8080/routes";
    private final String GROUP_URL = "http://localhost:8000/groups";
    private final String AUTH_URL = "http://localhost:8087/auth/validate";

    public CommunicationService() {
        this.restTemplate = new RestTemplate();
    }

    // ---------------- Students ----------------
    public Student[] getAllStudents() {
        return restTemplate.getForObject(STUDENT_URL, Student[].class);
    }

    public Student getStudentById(Long id) {
        return restTemplate.getForObject(STUDENT_URL + "/" + id, Student.class);
    }

    public Student addStudent(Student s) {
        return restTemplate.postForObject(STUDENT_URL, s, Student.class);
    }

    public Student updateStudent(Long id, Student s) {
        restTemplate.put(STUDENT_URL + "/" + id, s);
        return getStudentById(id);
    }

    public void deleteStudent(Long id) {
        restTemplate.delete(STUDENT_URL + "/" + id);
    }

    // ---------------- Bus ----------------
    public Bus[] getAllBuses() {
        return restTemplate.getForObject(BUS_URL, Bus[].class);
    }

    public Bus getBusById(Long id) {
        return restTemplate.getForObject(BUS_URL + "/" + id, Bus.class);
    }

    public Bus addBus(Bus b) {
        return restTemplate.postForObject(BUS_URL, b, Bus.class);
    }

    public Bus updateBus(Long id, Bus b) {
        restTemplate.put(BUS_URL + "/" + id, b);
        return getBusById(id);
    }

    public void deleteBus(Long id) {
        restTemplate.delete(BUS_URL + "/" + id);
    }

    // ---------------- GPS ----------------
    public GPSLocation getLocation(Long entityId) {
        return restTemplate.getForObject(GPS_URL + "/" + entityId, GPSLocation.class);
    }

    public GPSLocation updateStudentLocation(GPSLocation loc) {
        return restTemplate.postForObject(GPS_URL + "/student", loc, GPSLocation.class);
    }

    public GPSLocation updateBusLocation(GPSLocation loc) {
        return restTemplate.postForObject(GPS_URL + "/bus", loc, GPSLocation.class);
    }

    // ---------------- Notifications ----------------
    public ApiResponse sendNotification(Notification n) {
        // renvoie un objet JSON unique → ApiResponse
        return restTemplate.postForObject(NOTIFICATION_URL + "/send", n, ApiResponse.class);
    }

    public ApiResponse[] getNotificationHistory(Long userId) {
        // renvoie un tableau JSON → ApiResponse[]
        return restTemplate.getForObject(NOTIFICATION_URL + "/history/" + userId, ApiResponse[].class);
    }



    // ---------------- Routes ----------------
    public Route[] getOptimalRoutes() {
        return restTemplate.getForObject(ROUTE_URL + "/optimal", Route[].class);
    }

    public String getETA(Long studentId) {
        return restTemplate.getForObject(ROUTE_URL + "/eta/" + studentId, String.class);
    }

    public Route[] generateRoutes() {
        return restTemplate.postForObject(ROUTE_URL + "/generate", null, Route[].class);
    }

    // ---------------- Groups ----------------
    public Group[] getAllGroups() {
        return restTemplate.getForObject(GROUP_URL, Group[].class);
    }

    public Group getGroupById(Long id) {
        return restTemplate.getForObject(GROUP_URL + "/" + id, Group.class);
    }

    public Group[] generateGroups() {
        return restTemplate.postForObject(GROUP_URL + "/generate", null, Group[].class);
    }

    public Group updateGroup(Long id, String nom, Integer taille) {
        String url = GROUP_URL + "/" + id + "?nom=" + nom + "&taille=" + taille;
        restTemplate.put(url, null);
        return getGroupById(id);
    }

    public void deleteGroup(Long id) {
        restTemplate.delete(GROUP_URL + "/" + id);
    }

    // ---------------- Auth ----------------
    public TokenValidationResponse validateToken(String token) {
        try {
            if (token == null) throw new RuntimeException("Token manquant");
            if (!token.startsWith("Bearer ")) token = "Bearer " + token;

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", token);

            HttpEntity<Void> entity = new HttpEntity<>(headers);
            return restTemplate.postForObject(AUTH_URL, entity, TokenValidationResponse.class);

        } catch (Exception e) {
            TokenValidationResponse resp = new TokenValidationResponse();
            resp.setValid(false);
            resp.setMessage("Erreur de communication avec Auth-service: " + e.getMessage());
            return resp;
        }
    }

    // ---------------- Inter-microservice ----------------

    // Group ↔ Student
    public Map<String, Object> getGroupWithStudents(Long groupId) {
        Group group = getGroupById(groupId);
        Student[] students = getAllStudents();
        List<Student> studentsInGroup = Arrays.stream(students)
                .filter(s -> s.getGroupeId() != null && s.getGroupeId().equals(groupId)) // <-- utiliser groupeId
                .collect(Collectors.toList());

        Map<String, Object> map = new HashMap<>();
        map.put("group", group);
        map.put("students", studentsInGroup);
        return map;
    }


    // Bus ↔ Student
    public Map<String,Object> getBusWithStudents(Long busId) {
        Bus bus = getBusById(busId);
        Student[] students = getAllStudents();
        List<Student> studentsInBus = Arrays.stream(students)
                .filter(s -> s.getBusId() != null && s.getBusId().equals(busId))
                .collect(Collectors.toList());
        Map<String,Object> map = new HashMap<>();
        map.put("bus", bus);
        map.put("students", studentsInBus);
        return map;
    }

    // Bus ↔ GPS
    public Map<String,Object> getBusWithLocation(Long busId) {
        Bus bus = getBusById(busId);
        GPSLocation location = getLocation(busId);
        Map<String,Object> map = new HashMap<>();
        map.put("bus", bus);
        map.put("location", location);
        return map;
    }

    // Student ↔ Routes
    public Map<String,Object> getStudentWithETA(Long studentId) {
        Student student = getStudentById(studentId);
        String eta = getETA(studentId);
        Map<String,Object> map = new HashMap<>();
        map.put("student", student);
        map.put("eta", eta);
        return map;
    }
}
