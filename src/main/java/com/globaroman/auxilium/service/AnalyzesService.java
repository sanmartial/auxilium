package com.globaroman.auxilium.service;

import com.globaroman.auxilium.model.entity.Analyzes;
import com.globaroman.auxilium.model.repository.AnalyzesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalyzesService {
    private final AnalyzesRepository analyzesRepository;

    @Autowired
    public AnalyzesService(AnalyzesRepository analyzesRepository) {
        this.analyzesRepository = analyzesRepository;
    }

    public Analyzes createAnalyzes(Analyzes analyzes) {
        return analyzesRepository.save(analyzes);
    }

    public List<Analyzes> getAllAnalyseById(Long id) {
        return analyzesRepository.findAllByDiagnosisId(id);
    }
}
