package com.globaroman.auxilium.controller;

import com.globaroman.auxilium.model.entity.cabinet.Analyzes;
import com.globaroman.auxilium.model.entity.UserAUX;
import com.globaroman.auxilium.service.AnalyzesService;
import com.globaroman.auxilium.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cabinet")
public class AnalyzeController {
    private final UserService userService;
    private final AnalyzesService analyzesService;

    @GetMapping("/newAnalyzes/{id}")
    public String getPageAnalyzes(@PathVariable Long id, Model model) {
        model.addAttribute("diagnosis_id", id);
        return "analyzes_form";
    }

    @GetMapping("/newAnalyzes")
    public String getPageSimpleAnalyzes(Model model) {
        model.addAttribute("diagnosis_id", 555);
        return "analyzes_form";
    }

    @PostMapping("/newAnalyzes")
    public String createNewAnalizes(@RequestParam(value = "analysisDate") LocalDate date_analyze,
                                    @RequestParam(value = "analysisType") String type_analyzes,
                                    @RequestParam(value = "description") String description,
                                    @RequestParam(value = "analysisResult") String analysisResult,
                                    @RequestParam(value = "pathFile") String pathFile,
                                    @RequestParam(value = "diagnosis_id") Long diagnosis_id,
                                    Authentication authentication) {
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
        return "redirect:/cabinet/diagnosis/" + diagnosis_id;
    }

    @GetMapping("/analyzes/{id}")
    public String getPAgeAnalyzeDetails(@PathVariable("id") Long id, Model model) {
        Analyzes analyzes = analyzesService.getAnalyzeById(id);
        model.addAttribute("analyzes", analyzes);
        return "analyzes_detail";
    }

    @PostMapping("/analyzes/{id}")
    public ResponseEntity<String> deleteAnalyze(@PathVariable("id") Long id) {
        String result = analyzesService.deleteAnalyses(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/analyzes/edit/{id}")
    public String getPageAnalyzeForEdit(@PathVariable("id") Long id, Model model) {
        Analyzes analyzes = analyzesService.getAnalyzeById(id);
        model.addAttribute("analyzes", analyzes);
        return "analyzes_form_edit";
    }

    @PostMapping("/analyzes/edit/{id}")
    public String updateAnalyze(@RequestParam(value = "analysisDate") LocalDate date,
                                @RequestParam(value = "analysisType") String type,
                                @RequestParam(value = "description") String description,
                                @RequestParam(value = "analysisResult") String analysisResult,
                                @RequestParam(value = "pathFile") String pathFile,
                                @PathVariable("id") Long id
    ) {
        Analyzes analyzes = analyzesService.getAnalyzeById(id);
        analyzes.setDate_analyze(date);
        analyzes.setDescription_analyzes(description);
        analyzes.setResult_analyze(analysisResult);
        analyzes.setType_analyzes(type);
        analyzes.setPathScan(pathFile);
        analyzesService.updateAnalyzed(analyzes);
        return "redirect:/cabinet/analyzes/" + analyzes.getId();
    }

    @GetMapping("/allAnalyzes")
    public String getAllAnalyzes(Model model) {
        List<Analyzes> lists = analyzesService.getAllAnalyzes();
        model.addAttribute("Analyzeslists", lists);
        return "historyAnalyzes";
    }


    private String getUserID(Authentication authentication) {
        String username = authentication.getName();
        UserAUX userAUX = userService.getUserByUsername(username);
        return userAUX.getUser_id();
    }
}
