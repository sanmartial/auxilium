package com.globaroman.auxilium.controller;

import com.globaroman.auxilium.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/cabinet_patient")
    public String getPagePatient() {
        return "cabinet_patient";
    }

    @GetMapping("/cabinet_doctor")
    public String getPageDoctor() {
        return "cabinet_doctor";
    }
}
