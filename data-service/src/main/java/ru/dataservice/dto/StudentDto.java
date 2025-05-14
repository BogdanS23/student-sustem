package ru.dataservice.dto;

import lombok.*;
import ru.dataservice.entity.Student;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
    private String firstName;
    private String lastName;
    private String groupName;
    private List<GradeDto> grades;

    public StudentDto(Student student) {
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        this.groupName = student.getGroupName();
        this.grades = student.getGrades().stream()
                .map(GradeDto::new)
                .toList();
    }

    public Student toEntity() {
        Student student = new Student();
        student.setFirstName(this.firstName);
        student.setLastName(this.lastName);
        student.setGroupName(this.groupName);
        return student;
    }
}