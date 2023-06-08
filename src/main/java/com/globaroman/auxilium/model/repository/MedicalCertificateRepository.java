package com.globaroman.auxilium.model.repository;

import com.globaroman.auxilium.model.entity.cabinet.MedicalCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalCertificateRepository extends JpaRepository<MedicalCertificate, Long> {
    @Query("select m from  MedicalCertificate m where m.diagnosis_id = :id")
    MedicalCertificate findByDiagnosisId(@Param("id") Long id);

    @Query("select m from  MedicalCertificate m where m.diagnosis_id = :id")
    List<MedicalCertificate> findAllByDiagnosisId(Long id);

    @Query("delete from MedicalCertificate m where m.diagnosis_id = :id")
    void deleteByDiagnosisId(@Param("id") Long id);
}
