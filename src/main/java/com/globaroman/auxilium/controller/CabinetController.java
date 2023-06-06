package com.globaroman.auxilium.controller;

import com.globaroman.auxilium.model.entity.Diagnosis;
import com.globaroman.auxilium.model.entity.UserAUX;
import com.globaroman.auxilium.model.repository.DiagnosisRepository;
import com.globaroman.auxilium.service.DiagnosisService;
import com.globaroman.auxilium.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final DiagnosisRepository diagnosisRepository;

    @Autowired
    public CabinetController(UserService userService, DiagnosisService diagnosisService,
                             DiagnosisRepository diagnosisRepository) {
        this.userService = userService;
        this.diagnosisService = diagnosisService;
        this.diagnosisRepository = diagnosisRepository;
    }

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

    private String getUserID(Authentication authentication) {
        String username = authentication.getName();
        UserAUX userAUX = userService.getUserByUsername(username);
        return userAUX.getUser_id();
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
        model.addAttribute("diagnosis", diagnosis);
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

}