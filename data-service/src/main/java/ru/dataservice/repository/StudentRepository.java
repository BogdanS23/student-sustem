package ru.dataservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.dataservice.entity.Student;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByFirstNameAndLastNameAndGroupName(
            String firstName,
            String lastName,
            String groupName
    );

    List<Student> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
            String firstName, String lastName);

    List<Student> findByGroupName(String groupName);

    @Query("SELECT s FROM Student s " +
            "WHERE (SELECT AVG(g.value) FROM s.grades g) > :minAverage")
    List<Student> findByAverageGradeGreaterThan(Double minAverage);

    @Query("SELECT DISTINCT s FROM Student s " +
            "JOIN s.grades g WHERE g.subject = :subject")
    List<Student> findStudentsWithSubjectGrades(String subject);
}