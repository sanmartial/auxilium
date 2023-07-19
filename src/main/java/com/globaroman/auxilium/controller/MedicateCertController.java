package com.globaroman.auxilium.controller;

import com.globaroman.auxilium.model.entity.UserAUX;
import com.globaroman.auxilium.model.entity.cabinet.MedicalCertificate;
import com.globaroman.auxilium.service.MedicalCertificateService;
import com.globaroman.auxilium.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cabinet/diagnosis")
public class MedicateCertController {
    String path = "C:\\Users\\globa\\OneDrive\\Изображения\\AUXStorage\\CCF18102022_0008.pdf";
    private final MedicalCertificateService certificateService;
    private final UserService userService;

    @GetMapping("/newCertificate/{id}")
    public String getPageCertificate(@PathVariable("id") Long id, Model model) {
        model.addAttribute("diagnosis_id", id);
        return "certificate_form";
    }

    @PostMapping("/newCertificate")
    public String createNewCertificate(@RequestParam(value = "dateIssue") LocalDate dateIssue,
                                       @RequestParam(value = "description") String description,
                                       @RequestParam(value = "pathFile") String pathFile,
                                       @RequestParam(value = "diagnosis_id") Long diagnosis_id, Authentication authentication) {
MedicalCertificate certificate = new MedicalCertificate().builder()
        .patient_id(getUserID(authentication))
        .description(description)
        .dateIssue(dateIssue)
        .diagnosis_id(diagnosis_id)
        .pathScan(pathFile)
        .build();
        certificateService.createNewCertificate(certificate);
   return"redirect:/cabinet/diagnosis/" + diagnosis_id; }

    @GetMapping("/certificate/{id}")
    public String getPageCertificateDetail(@PathVariable("id") Long id, Model model) {
        MedicalCertificate certificate = certificateService.getCertificateById(id);
        model.addAttribute("certificate", certificate);
        return "certificate_detail"; }


//    @GetMapping("/certificate/{id}")
//    public void openFile(@PathVariable("id") Long id) throws IOException {
//
//        MedicalCertificate certificate = certificateService.getCertificateById(id);
//        String path = getPropPath(certificate.getPathScan());
//        Desktop.getDesktop().open(new File(path));
//        //Desktop.getDesktop().open(new File(System.getProperty("user.home") + "\\ErdrPlus\\listOne.txt"));
//    }

    private String getPropPath(String pathScan) {

        return null;
    }

    private String getUserID(Authentication authentication) {
        String username = authentication.getName();
        UserAUX userAUX = userService.getUserByUsername(username);
        return userAUX.getUser_id();
    }
}
