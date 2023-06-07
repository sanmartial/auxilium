package com.globaroman.auxilium.controller;

import com.globaroman.auxilium.model.entity.Analyzes;
import com.globaroman.auxilium.model.entity.Diagnosis;
import com.globaroman.auxilium.model.entity.UserAUX;
import com.globaroman.auxilium.model.repository.DiagnosisRepository;
import com.globaroman.auxilium.service.AnalyzesService;
import com.globaroman.auxilium.service.DiagnosisService;
import com.globaroman.auxilium.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/cabinet")
public class CabinetController {
    private final UserService userService;
    private final DiagnosisService diagnosisService;
    private final AnalyzesService analyzesService;

    @Autowired
    public CabinetController(UserService userService, DiagnosisService diagnosisService,
                             DiagnosisRepository diagnosisRepository, AnalyzesService analyzesService) {
        this.userService = userService;
        this.diagnosisService = diagnosisService;
        this.analyzesService = analyzesService;
    }

    @GetMapping()
    public String getPageCabinet(Model model, Authentication authentication) {

        String username = authentication.getName();
        UserAUX userAUX = userService.getUserByUsername(username);

        if ("DOCTOR".equals(userAUX.getRole().getAuthority())) {
            model.addAttribute("username", username);
            return "cabinet_doctor";
        } if (userAUX.getRole().getAuthority().equals("PATIENT") ) {
            model.addAttribute("username", username);
            return "cabinet_patient";
        }
        if (userAUX.getRole().getAuthority().equals("ADMIN") ) {
            model.addAttribute("username", username);
            return "cabinet_admin";
        }

        return "main_page"; }
    @GetMapping("/newDiagnosis")
    public String getPageDiagnosis() {
        return "diagnosis_form";
    }

    @PostMapping("/newDiagnosis")
    public String createNewDiagnosis(@RequestParam(value = "diseaseName") String diseaseName,
                                     @RequestParam(value = "symptoms") String symptoms, Authentication authentication) {
        String user_id = getUserID(authentication);
        diagnosisService.createDiagnosis(Diagnosis.builder()
                .diagnosis_name(diseaseName)
                .description(symptoms)
                .date(LocalDate.now())
                .patient_id(user_id)
                .build());

        return "redirect:/cabinet/historyDiagnosis";
    }

    @GetMapping("/historyDiagnosis")
    public String getAllHistoryDiagnosises(Model model, Authentication authentication) {
        String user_id = getUserID(authentication);
        List<Diagnosis> list = diagnosisService.getAllHistoryDiagnisis(user_id);
        model.addAttribute("diagnosisList", list);
        return "historyDiagnosis";
    }

    @GetMapping("/diagnosis/{id}")
    public String getPageDiagnosis(@PathVariable("id") Long id, Model model) {
        Diagnosis diagnosis = diagnosisService.getDiagnosisById(id);
        List<Analyzes> analyzesList = analyzesService.getAllAnalyseById(diagnosis.getId());
        model.addAttribute("diagnosis", diagnosis);
        model.addAttribute("analyzesList", analyzesList);
        return "diagnosis";
    }

    @PostMapping("/diagnosis/{id}")
    public ResponseEntity<String> deleteDiagnosis(@PathVariable("id") Long id) {
        String result = diagnosisService.deleteDiagnosis(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/diagnosis/edit/{id}")
    public String editDiagnosis(@PathVariable Long id, Model model) {
        Diagnosis diagnosis = diagnosisService.getDiagnosisById(id);
        model.addAttribute("diagnosis", diagnosis);
        return "diagnosis_form_edit";
    }

    @PostMapping("/diagnosis/edit/{id}")
    public String updateDiagnosis(@RequestParam(value = "diseaseName") String diseaseName,
                                  @RequestParam(value = "symptoms") String symptoms,
                                  @PathVariable("id") Long id, Authentication authentication) {
        Diagnosis diagnosis = diagnosisService.getDiagnosisById(id);
        diagnosis.setDiagnosis_name(diseaseName);
        diagnosis.setDescription(symptoms);
        diagnosisService.updateDiagnosis(diagnosis);

        return "redirect:/cabinet/historyDiagnosis";
    }

    @GetMapping("/newAnalyzes/{id}")
    public String getPageAnalyzes(@PathVariable Long id, Model model) {
        model.addAttribute("diagnosis_id", id);
        return "analyzes_form";}

    @PostMapping("/newAnalyzes")
    public String createNewAnalizes(@RequestParam(value = "analysisDate") LocalDate date_analyze,
                                    @RequestParam(value = "analysisType") String type_analyzes,
                                    @RequestParam(value = "description") String description,
                                    @RequestParam(value = "analysisResult") String analysisResult,
                                    @RequestParam(value = "pathFile") String pathFile,
                                    @RequestParam("diagnosis_id") Long diagnosis_id,
                                    Authentication authentication) {
        System.out.println("Создаю новый анализ");
        String user_id = getUserID(authentication);
        Analyzes analyzes = new Analyzes()
                .builder()
                .diagnosis_id(diagnosis_id)
                .date_analyze(date_analyze)
                .description_analyzes(description)
                .type_analyzes(type_analyzes)
                .result_analyze(analysisResult)
                .patient_id(user_id)
                .pathScan(pathFile)
                .build();
        analyzesService.createAnalyzes(analyzes);
        System.out.println("Создал новый анализ");
        return "redirect:/cabinet/diagnosis/" + diagnosis_id;}

    private String getUserID(Authentication authentication) {
        String username = authentication.getName();
        UserAUX userAUX = userService.getUserByUsername(username);
        return userAUX.getUser_id();
    }

}
