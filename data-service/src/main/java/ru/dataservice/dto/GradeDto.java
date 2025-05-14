package ru.dataservice.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.dataservice.entity.Grade;
import ru.dataservice.entity.Student;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GradeDto {
    String studentFirstName;
    String studentLastName;
    String groupName;
    String subject;
    Double value;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    LocalDate date;

    public GradeDto(Grade grade) {
        Student student = grade.getStudent();
        this.studentFirstName = student.getFirstName();
        this.studentLastName = student.getLastName();
        this.groupName = student.getGroupName();
        this.subject = grade.getSubject();
        this.value = grade.getValue();
        this.date = grade.getDate();
    }
}