package com.globaroman.auxilium.service;

import com.globaroman.auxilium.model.entity.Diagnosis;
import com.globaroman.auxilium.model.repository.DiagnosisRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiagnosisService {
    private final DiagnosisRepository diagnosisRepository;

    @Autowired
    public DiagnosisService(DiagnosisRepository diagnosisRepository) {
        this.diagnosisRepository = diagnosisRepository;
    }

    public List<Diagnosis> getAllHistoryDiagnisis(String userId) {
        return diagnosisRepository.findAllDiagnosisByUserId(userId);
    }

    public Diagnosis createDiagnosis(Diagnosis diagnosis) {
        return diagnosisRepository.save(diagnosis);
    }

    public Diagnosis getDiagnosisById(Long id) {
        return diagnosisRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Diagnosis not found"));
    }

    public String deleteDiagnosis(Long id) {
        diagnosisRepository.deleteById(id);
        if (diagnosisRepository.findById(id).isPresent()) {
            return "Diagnosis with id " + id + " wasn't deleted";
        } else {
            return "Diagnosis with id " + id + " was deleted";
        }
    }

    public void updateDiagnosis(Diagnosis diagnosis) {
        diagnosisRepository.save(diagnosis);
    }
}
