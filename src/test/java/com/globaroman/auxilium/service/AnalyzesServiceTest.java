package com.globaroman.auxilium.service;

import com.globaroman.auxilium.IntegrationTestBase;
import com.globaroman.auxilium.model.entity.cabinet.Analyzes;
import com.globaroman.auxilium.model.entity.cabinet.Diagnosis;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AnalyzesServiceTest extends IntegrationTestBase {
    @Autowired
    private DiagnosisService diagnosisService;
    @Autowired
    private AnalyzesService analyzesService;
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createAnalyzes() {
    }

    @Test
    void getAllAnalyseById() {
    }

    @Test
    void getAnalyzeById() {
    }

    @Test
    void deleteAnalyses() {
    }

    @Test
    void updateAnalyzed() {
    }

    @Test
    void testDeleteAnalysesByDiagnosisId() {
        Diagnosis diagnosis = diagnosisService.createDiagnosis(new Diagnosis(22L, "s19192812989", "djfdfdk", "sdkskdlsd", LocalDate.now()));
        Analyzes analyzes = analyzesService.createAnalyzes(new Analyzes(22L, "kdkfdk", 22L, "skksjsk", "lsdksldksl", "skjsk", LocalDate.now(), "sllsllssl"));
        analyzesService.deleteAnalyses(analyzes.getId());
        assertThrows(EntityNotFoundException.class, () -> analyzesService.getAnalyzeById(analyzes.getId()));
        ;
    }
}