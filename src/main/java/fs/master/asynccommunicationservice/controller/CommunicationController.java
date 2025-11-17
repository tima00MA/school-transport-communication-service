package fs.master.asynccommunicationservice.controller;

import org.springframework.web.bind.annotation.*;
import fs.master.asynccommunicationservice.model.*;
import fs.master.asynccommunicationservice.service.CommunicationService;

import java.util.Map;

@RestController
@RequestMapping("/api/communication")
public class CommunicationController {

    private final CommunicationService service;

    public CommunicationController(CommunicationService service) {
        this.service = service;
    }

    // ---------------- Students ----------------
    @GetMapping("/students")
    public Object getStudents() {
        return service.getAllStudents();
    }

    @GetMapping("/students/{id}")
    public Object getStudent(@PathVariable Long id) {
        return service.getStudentById(id);
    }

    @PostMapping("/students")
    public Object addStudent(@RequestBody Student s) {
        return service.addStudent(s);
    }

    @PutMapping("/students/{id}")
    public Object updateStudent(@PathVariable Long id, @RequestBody Student s) {
        return service.updateStudent(id, s);
    }

    @DeleteMapping("/students/{id}")
    public void deleteStudent(@PathVariable Long id) {
        service.deleteStudent(id);
    }

    // ---------------- Bus ----------------
    @GetMapping("/buses")
    public Object getBuses() {
        return service.getAllBuses();
    }

    @GetMapping("/buses/{id}")
    public Object getBus(@PathVariable Long id) {
        return service.getBusById(id);
    }

    @PostMapping("/buses")
    public Object addBus(@RequestBody Bus b) {
        return service.addBus(b);
    }

    @PutMapping("/buses/{id}")
    public Object updateBus(@PathVariable Long id, @RequestBody Bus b) {
        return service.updateBus(id, b);
    }

    @DeleteMapping("/buses/{id}")
    public void deleteBus(@PathVariable Long id) {
        service.deleteBus(id);
    }

    // ---------------- GPS ----------------
    @GetMapping("/location/{entityId}")
    public Object getLocation(@PathVariable Long entityId) {
        return service.getLocation(entityId);
    }

    @PostMapping("/location/student")
    public Object updateStudentLocation(@RequestBody GPSLocation loc) {
        return service.updateStudentLocation(loc);
    }

    @PostMapping("/location/bus")
    public Object updateBusLocation(@RequestBody GPSLocation loc) {
        return service.updateBusLocation(loc);
    }

    // ---------------- Notifications ----------------
    @PostMapping("/notifications/send")
    public Object sendNotification(@RequestBody Notification n) {
        return service.sendNotification(n);
    }

    @GetMapping("/notifications/history/{userId}")
    public Object getNotificationHistory(@PathVariable Long userId) {
        return service.getNotificationHistory(userId);
    }

    // ---------------- Routes ----------------
    @GetMapping("/routes/optimal")
    public Object getOptimalRoutes() {
        return service.getOptimalRoutes();
    }

    @GetMapping("/routes/eta/{studentId}")
    public Object getETA(@PathVariable Long studentId) {
        return service.getStudentWithETA(studentId);
    }

    @PostMapping("/routes/generate")
    public Object generateRoutes() {
        return service.generateRoutes();
    }

    // ---------------- Groups ----------------
    @GetMapping("/groups")
    public Object getGroups() {
        return service.getAllGroups();
    }

    @GetMapping("/groups/{id}")
    public Object getGroup(@PathVariable Long id) {
        return service.getGroupById(id);
    }

    @PostMapping("/groups/generate")
    public Object generateGroups() {
        return service.generateGroups();
    }

    @PutMapping("/groups/{id}")
    public Object updateGroup(@PathVariable Long id,
                              @RequestParam String nom,
                              @RequestParam Integer taille) {
        return service.updateGroup(id, nom, taille);
    }

    @DeleteMapping("/groups/{id}")
    public void deleteGroup(@PathVariable Long id) {
        service.deleteGroup(id);
    }

    // ---------------- Auth ----------------
    @PostMapping("/validate")
    public TokenValidationResponse validate(@RequestHeader(value = "Authorization", required = false) String token) {
        return service.validateToken(token);
    }

    // ---------------- Inter-microservice ----------------

    // Group ↔ Student
    @GetMapping("/groups/{id}/students")
    public Map<String, Object> getGroupWithStudents(@PathVariable Long id) {
        return service.getGroupWithStudents(id);
    }

    // Bus ↔ Student
    @GetMapping("/buses/{id}/students")
    public Map<String, Object> getBusWithStudents(@PathVariable Long id) {
        return service.getBusWithStudents(id);
    }

    // Bus ↔ GPS
    @GetMapping("/buses/{id}/location")
    public Map<String, Object> getBusWithLocation(@PathVariable Long id) {
        return service.getBusWithLocation(id);
    }

    // Student ↔ Routes
    @GetMapping("/students/{id}/eta")
    public Map<String, Object> getStudentWithETA(@PathVariable Long id) {
        return service.getStudentWithETA(id);
    }
}
