package ru.dataservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dataservice.dto.StudentDto;
import ru.dataservice.entity.Student;
import ru.dataservice.repository.StudentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public List<StudentDto> searchStudents(String query) {
        List<Student> students;
        if (query == null || query.isBlank()) {
            students = studentRepository.findAll();
        } else {
            students = studentRepository
                    .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(query, query);
        }
        return convertToDtoList(students);
    }

    public List<StudentDto> getHonorStudents() {
        return convertToDtoList(
                studentRepository.findByAverageGradeGreaterThan(4.75)
        );
    }

    public List<StudentDto> getStudentsBySubject(String subject) {
        return convertToDtoList(
                studentRepository.findStudentsWithSubjectGrades(subject)
        );
    }

    public List<StudentDto> getStudentsByGroup(String groupName) {
        return convertToDtoList(
                studentRepository.findByGroupName(groupName)
        );
    }

    private List<StudentDto> convertToDtoList(List<Student> students) {
        return students.stream()
                .map(StudentDto::new)
                .collect(Collectors.toList());
    }
}