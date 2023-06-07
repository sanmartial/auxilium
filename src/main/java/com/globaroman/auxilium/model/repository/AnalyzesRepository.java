package com.globaroman.auxilium.model.repository;

import com.globaroman.auxilium.model.entity.Analyzes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnalyzesRepository extends JpaRepository<Analyzes, Long> {

    @Query("select a from Analyzes a where a.diagnosis_id = :id")
    List<Analyzes> findAllByDiagnosisId(@Param("id") Long id);
}
