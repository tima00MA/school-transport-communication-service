package fs.master.asynccommunicationservice.feign;


import fs.master.asynccommunicationservice.model.Student;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "student-service", url = "${services.student.url}")
public interface StudentClient {

    @GetMapping("/api/students")
    List<Student> getAllStudents();

    @GetMapping("/api/students/{id}")
    Student getStudentById(@PathVariable("id") Long id);

    @PostMapping("/api/students")
    Student addStudent(@RequestBody Student student);

    @PutMapping("/api/students/{id}")
    Student updateStudent(@PathVariable("id") Long id, @RequestBody Student student);

    @DeleteMapping("/api/students/{id}")
    void deleteStudent(@PathVariable("id") Long id);
}