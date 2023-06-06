package com.globaroman.auxilium.model.repository;

import com.globaroman.auxilium.model.entity.Diagnosis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface DiagnosisRepository extends JpaRepository<Diagnosis, Long> {
    @Transactional
    @Modifying
    @Query("update Diagnosis d set d.id = ?1 where d.diagnosis_name = ?2 and d.description = ?3")
    int updateIdByDiagnosis_nameAndDescription(Long id, String diagnosis_name, String description);
    @Query("select d from Diagnosis d where d.patient_id = :user_id")
    List<Diagnosis> findAllDiagnosisByUserId(@Param("user_id")String user_id);
}
