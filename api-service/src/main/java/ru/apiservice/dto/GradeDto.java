package ru.apiservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GradeDto{
        String studentFirstName;
        String studentLastName;
        String groupName;
        String subject;
        Double value;
        LocalDate date;
}
