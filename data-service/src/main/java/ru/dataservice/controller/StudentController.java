package ru.dataservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dataservice.dto.StudentDto;
import ru.dataservice.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/reports/honor")
    public ResponseEntity<List<StudentDto>> getHonorStudents() {
        return ResponseEntity.ok(studentService.getHonorStudents());
    }

    @GetMapping("/reports/by-subject")
    public ResponseEntity<List<StudentDto>> getBySubject(@RequestParam String subject) {
        return ResponseEntity.ok(studentService.getStudentsBySubject(subject));
    }

    @GetMapping("/reports/by-group")
    public ResponseEntity<List<StudentDto>> getByGroup(@RequestParam String groupName) {
        return ResponseEntity.ok(studentService.getStudentsByGroup(groupName));
    }
}