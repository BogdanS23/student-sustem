package ru.dataservice.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.dataservice.dto.GradeDto;
import ru.dataservice.entity.Grade;
import ru.dataservice.entity.Student;
import ru.dataservice.repository.GradeRepository;
import ru.dataservice.repository.StudentRepository;

@Component
public class GradeConsumer {

    private final StudentRepository studentRepository;
    private final GradeRepository gradeRepository;

    public GradeConsumer(StudentRepository studentRepository, GradeRepository gradeRepository) {
        this.studentRepository = studentRepository;
        this.gradeRepository = gradeRepository;
    }

    @KafkaListener(topics = "grades-topic", groupId = "grade-group", containerFactory = "gradeKafkaListenerFactory")
    public void consumeGrade(GradeDto dto) {
        Student student = studentRepository
                .findByFirstNameAndLastNameAndGroupName(
                        dto.getStudentFirstName(),
                        dto.getStudentLastName(),
                        dto.getGroupName()
                )
                .orElseGet(() -> {
                    Student newStudent = new Student();
                    newStudent.setFirstName(dto.getStudentFirstName());
                    newStudent.setLastName(dto.getStudentLastName());
                    newStudent.setGroupName(dto.getGroupName());
                    return studentRepository.save(newStudent);
                });


        Grade grade = new Grade();
        grade.setStudent(student);
        grade.setSubject(dto.getSubject());
        grade.setValue(dto.getValue());
        grade.setDate(dto.getDate());
        gradeRepository.save(grade);
    }
}
