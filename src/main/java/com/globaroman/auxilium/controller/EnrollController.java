package com.globaroman.auxilium.controller;

import com.globaroman.auxilium.config.PasswordEncoderConfig;
import com.globaroman.auxilium.model.entity.security.RoleAUX;
import com.globaroman.auxilium.model.entity.security.Status;
import com.globaroman.auxilium.model.entity.UserAUX;
import com.globaroman.auxilium.service.UserService;
import com.globaroman.auxilium.view.TextConstants;
import com.globaroman.auxilium.view.ViewAUX;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.globaroman.auxilium.view.TextConstants.MESSAGE_WRONG_INPUT_DATA;

//@RestController
@Controller
@RequestMapping("/")
public class EnrollController {
    private final UserService userService;
    private final PasswordEncoderConfig passwordEncoderConfig;

    @Autowired
    public EnrollController(UserService userService, PasswordEncoderConfig passwordEncoderConfig) {
        this.userService = userService;

        this.passwordEncoderConfig = passwordEncoderConfig;
    }

    @GetMapping("/main")
    public String getMainPage(Authentication authentication) {
        try {
            String userName = authentication.getName();

            if (!authentication.isAuthenticated() || userName.equals("admin")) {
                return "main_page";
            } else {
                return "main_page";
            }
        } catch (NullPointerException e) {
            return "main_page";
        }
    }

    @GetMapping("/new")
    public String getPage(Authentication authentication)
    {
        try {
        String userName = authentication.getName();
        if (!authentication.isAuthenticated() || userName.equals("admin")) {
        return "form_enrollment";}
        else {
            return "form_sussesfull";
        }
        } catch (NullPointerException e) {
            return "form_enrollment";}
        }

    @PostMapping("/new")
    public String createNewUser(@RequestParam(value = "userName") String userName,
                                @RequestParam(value = "password") String password,
                                @RequestParam(value = "firstName") String firstName,
                                @RequestParam(value = "lastName") String lastName,
                                @RequestParam(value = "birthdate") LocalDate birthday,
                                @RequestParam(value = "email") String email,
                                @RequestParam(value = "phoneNumber") String phoneNumber,
                                @RequestParam(value = "role") String role, Model model) {
        if (checkDataFromForm(userName, ViewAUX.bundleRegEX.getString(TextConstants.REGEX_USERNAME)) ||
                checkDataFromForm(password, ViewAUX.bundleRegEX.getString(TextConstants.REGEX_PASSWORD)) ||
                checkDataFromForm(firstName, ViewAUX.bundleRegEX.getString(TextConstants.REGEX_NAME)) ||
                checkDataFromForm(lastName, ViewAUX.bundleRegEX.getString(TextConstants.REGEX_NAME)) ||
                checkDataFromForm(email, ViewAUX.bundleRegEX.getString(TextConstants.REGEX_EMAIL)) ||
                checkDataFromForm(phoneNumber, ViewAUX.bundleRegEX.getString(TextConstants.REGEX_PHONENUMBER)
                )) {
            model.addAttribute("error", ViewAUX.printStringBundle(MESSAGE_WRONG_INPUT_DATA));
            return "form_enrollment";
        }

        if (checkUsernameDataBase(userName)) {
            UserAUX user = UserAUX.builder()
                    .user_id(String.valueOf(UUID.randomUUID()))
                    .username(userName)
                    .password(passwordEncoderConfig.passwordEncoder().encode(password))
                    .firstname(firstName)
                    .lastname(lastName)
                    .date_birth(birthday)
                    .date_logged(LocalDateTime.now())
                    .email(email)
                    .phonenumber(phoneNumber)
                    .role(RoleAUX.valueOf(role))
                    .status(Status.ACTIVE)
                    .build();
            userService.createUser(user);
            return "form_sussesfull";
        } else {
            model.addAttribute("error", "Username already exists.");
            return "form_enrollment";
        }
    }
    private boolean checkDataFromForm(String line, String regex) {
        System.out.println(line);
        System.out.println(regex);
        return !line.matches(regex);
    }

    private boolean checkUsernameDataBase(String userName) {
          try {
            userService.getUserByUsername(userName);
            return false;
        } catch (EntityNotFoundException e) {
            return true;
        }
    }
}
