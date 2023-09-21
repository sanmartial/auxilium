package com.globaroman.auxilium.service;

import com.globaroman.auxilium.model.entity.cabinet.Analyzes;
import com.globaroman.auxilium.model.repository.AnalyzesRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyzesService {
    private final AnalyzesRepository analyzesRepository;

    public Analyzes createAnalyzes(Analyzes analyzes) {
        return analyzesRepository.save(analyzes);
    }

    public List<Analyzes> getAllAnalyseByDiagnosisId(Long id) {
        return analyzesRepository.findAllByDiagnosisId(id);
    }

    public Analyzes getAnalyzeById(Long id) {
        return analyzesRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Analyze not found"));
    }

    public String deleteAnalyses(Long id) {
        analyzesRepository.deleteById(id);
        if (analyzesRepository.findById(id).isPresent()) {
            return "Analyze with id " + id + " wasn't deleted";
        } else {
            return "Analyze with id " + id + " was deleted";
        }

    }

    public void updateAnalyzed(Analyzes analyzes) {
        analyzesRepository.save(analyzes);
    }


    public List<Analyzes> getAllAnalyzes() {
        return analyzesRepository.findAll();
    }
}
