package ru.dataservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.dataservice.entity.Grade;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {


    @Query("SELECT g FROM Grade g WHERE " +
            "(:query IS NULL OR " +
            "g.student.firstName LIKE %:query% OR " +
            "g.student.lastName LIKE %:query% OR " +
            "g.subject LIKE %:query% OR " +
            "g.value = :numericQuery)")
    List<Grade> searchByQuery(
            @Param("query") String query,
            @Param("numericQuery") Double numericQuery
    );
}