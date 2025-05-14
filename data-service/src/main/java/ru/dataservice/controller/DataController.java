package ru.dataservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dataservice.dto.GradeDto;
import ru.dataservice.dto.StudentDto;
import ru.dataservice.service.GradeService;
import ru.dataservice.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/data")
public class DataController {

    private final GradeService gradeService;
    private final StudentService studentService;

    public DataController(GradeService gradeService, StudentService studentService) {
        this.gradeService = gradeService;
        this.studentService = studentService;
    }

    @GetMapping("/grades/search")
    public ResponseEntity<List<GradeDto>> searchGrades(@RequestParam(required = false) String query) {
        return ResponseEntity.ok(gradeService.searchGrades(query));
    }

    @GetMapping("/students/search")
    public ResponseEntity<List<StudentDto>> searchStudents(@RequestParam(required = false) String query) {
        return ResponseEntity.ok(studentService.searchStudents(query));
    }
}