package com.globaroman.auxilium.service;

import com.globaroman.auxilium.model.entity.cabinet.MedicalCertificate;
import com.globaroman.auxilium.model.repository.MedicalCertificateRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalCertificateService {
    private final MedicalCertificateRepository certificateRepository;

    @Autowired
    public MedicalCertificateService(MedicalCertificateRepository certificateRepository) {
        this.certificateRepository = certificateRepository;
    }

    public MedicalCertificate getCertificateById(Long id) {
        return certificateRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Certificate not found"));
    }

    public MedicalCertificate getCertificateByDiagnosisId(Long id) {
        return certificateRepository.findByDiagnosisId(id);
    }

    public MedicalCertificate createNewCertificate(MedicalCertificate certificate) {
        return certificateRepository.save(certificate);
    }

    public List<MedicalCertificate> getAllCertificateByDIagnosisId(Long id) {
        return certificateRepository.findAllByDiagnosisId(id);
    }

    public void deleteCertificateByDiagnosisId(Long id) {
        certificateRepository.deleteByDiagnosisId(id);
    }
}
