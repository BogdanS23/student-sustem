package ru.dataservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dataservice.dto.GradeDto;
import ru.dataservice.entity.Grade;
import ru.dataservice.repository.GradeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GradeService {

    private final GradeRepository gradeRepository;

    public List<GradeDto> searchGrades(String query) {
        Double numericQuery = tryParseDouble(query);
        List<Grade> grades = gradeRepository.searchByQuery(query, numericQuery);
        return grades.stream()
                .map(GradeDto::new)
                .collect(Collectors.toList());
    }

    private Double tryParseDouble(String input) {
        try {
            return Double.parseDouble(input);
        } catch (Exception e) {
            return null;
        }
    }
}