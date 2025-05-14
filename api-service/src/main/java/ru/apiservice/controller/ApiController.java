package ru.apiservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.apiservice.dto.GradeDto;
import ru.apiservice.dto.StudentDto;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final KafkaTemplate<String, GradeDto> kafkaTemplate;
    private final RestTemplate restTemplate;

    @Value("${data.service.url}")
    private String dataServiceUrl;

    public ApiController(KafkaTemplate<String, GradeDto> kafkaTemplate,
                             RestTemplate restTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.restTemplate = restTemplate;
    }

    @PostMapping("/grades")
    public ResponseEntity<String> addGrade(@RequestBody GradeDto grade) {
        kafkaTemplate.send("grades-topic", grade);
        return ResponseEntity.ok("Grade sent to Kafka");
    }

    @GetMapping("/grades/search")
    public ResponseEntity<?> searchGrades(@RequestParam(required = false) String query) {
        String url = buildUrl("/data/grades/search", query);
        return restTemplate.getForEntity(url, Object.class);
    }

    @GetMapping("/students/search")
    public ResponseEntity<List<StudentDto>> searchStudents(@RequestParam(required = false) String query) {
        String url = buildUrl("/data/students/search", query);
        RequestEntity<Void> request = RequestEntity.get(url).build();
        return restTemplate.exchange(request, new ParameterizedTypeReference<List<StudentDto>>() {});
        //return restTemplate.getForEntity(url, StudentDto[].class);
    }

    @GetMapping("/reports/honor-students")
    public ResponseEntity<List<StudentDto>> getHonorStudents() {
        RequestEntity<Void> request = RequestEntity.get(dataServiceUrl + "/students/reports/honor").build();
        return restTemplate.exchange(request, new ParameterizedTypeReference<List<StudentDto>>() {});
    }

    @GetMapping("/reports/students-by-subject")
    public ResponseEntity<List<StudentDto>> getStudentsBySubject(
            @RequestParam String subject) {
        String encodedSubject = URLEncoder.encode(subject, StandardCharsets.UTF_8);
        RequestEntity<Void> request = RequestEntity.get(dataServiceUrl + "/students/reports/by-subject?subject=" + encodedSubject).build();
        return restTemplate.exchange(request, new ParameterizedTypeReference<List<StudentDto>>() {});
    }

    @GetMapping("/reports/students-by-group")
    public ResponseEntity<List<StudentDto>> getStudentsByGroup(
            @RequestParam String groupName) {
        String encodedGroup = URLEncoder.encode(groupName, StandardCharsets.UTF_8);
        RequestEntity<Void> request = RequestEntity.get(dataServiceUrl + "/students/reports/by-group?groupName=" + encodedGroup).build();
        return restTemplate.exchange(request, new ParameterizedTypeReference<List<StudentDto>>() {});
    }

    private String buildUrl(String path, String query) {
        String url = dataServiceUrl + path;
        if (query != null && !query.isEmpty()) {
            url += "?query=" + URLEncoder.encode(query, StandardCharsets.UTF_8);
        }
        return url;
    }
}