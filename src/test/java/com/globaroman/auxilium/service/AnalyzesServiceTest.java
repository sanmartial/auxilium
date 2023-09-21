package com.globaroman.auxilium.service;

import com.globaroman.auxilium.IntegrationTestBase;
import com.globaroman.auxilium.model.entity.UserAUX;
import com.globaroman.auxilium.model.entity.cabinet.Analyzes;
import com.globaroman.auxilium.model.entity.cabinet.Diagnosis;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.UUID;

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
        Analyzes analyzes = new Analyzes()
                .builder()
                .diagnosis_id(1L)
                .patient_id(String.valueOf(UUID.randomUUID()))
                .diagnosis_id(1L)
                .type_analyzes("Мазок с гортани")
                .description_analyzes("Мазок с гортани")
                .result_analyze("патогенов не обнаружено.")
                .date_analyze(LocalDate.now())
                .pathScan("C:\\Users\\globa\\OneDrive\\Изображения\\AUXStorage\\CCF18102022_0005.pdf")
                .build();
        Analyzes analyzesAfter = analyzesService.createAnalyzes(analyzes);
        assertEquals(analyzes.getType_analyzes(), analyzesAfter.getType_analyzes());

    }
    @Test
    void getAllAnalyseByDiagnosisId() {
        Analyzes analyzes = analyzesService.getAllAnalyzes().stream().findFirst().orElseThrow();
        Analyzes analizesFirst = analyzesService.getAnalyzeById(analyzes.getId());
        assertNotNull(analizesFirst.getDiagnosis_id());
    }

    @Test
    void getAnalyzeById() {
        Analyzes analyzes = analyzesService.getAllAnalyzes().stream().findFirst().orElseThrow();
        Analyzes analizesFirst = analyzesService.getAnalyzeById(analyzes.getId());
        assertNotNull(analizesFirst.getId());
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