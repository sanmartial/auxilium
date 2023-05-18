package com.globaroman.auxilium.controller;

import com.globaroman.auxilium.model.entity.RoleAUX;
import com.globaroman.auxilium.model.entity.UserAUX;
import com.globaroman.auxilium.service.RoleService;
import com.globaroman.auxilium.service.UserService;
import com.globaroman.auxilium.view.TextConstants;
import com.globaroman.auxilium.view.ViewAUX;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ResourceBundle;

//@RestController
@Controller
@RequestMapping("/")
public class UserController {

    private final UserService userService;

    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/")
    public String getMainPage() {
        return "main_page";
    }
    @GetMapping("/new")
    public String getPage() {
        return "form_enrollment";
    }
    @PostMapping("/new")
    public String createNewUser(@RequestParam(value = "userName") String userName,
                                @RequestParam(value = "password") String password,
                                @RequestParam(value = "firstName") String firstName,
                                @RequestParam(value = "lastName") String lastName,
                                @RequestParam(value = "email") String email,
                                @RequestParam(value = "phoneNumber") String phoneNumber,
                                @RequestParam(value = "role") Long roleId, Model model) {


        if (checkDataFromForm(userName, ViewAUX.bundleRegEX.getString(TextConstants.REGEX_USERNAME)) ||
                checkDataFromForm(password, ViewAUX.bundleRegEX.getString(TextConstants.REGEX_PASSWORD)) ||
                checkDataFromForm(firstName, ViewAUX.bundleRegEX.getString(TextConstants.REGEX_NAME)) ||
                checkDataFromForm(lastName, ViewAUX.bundleRegEX.getString(TextConstants.REGEX_NAME)) ||
                checkDataFromForm(email, ViewAUX.bundleRegEX.getString(TextConstants.REGEX_EMAIL)) ||
                checkDataFromForm(phoneNumber, ViewAUX.bundleRegEX.getString(TextConstants.REGEX_PHONENUMBER)
                )) {
            model.addAttribute("error", ViewAUX.MESSAGE_WRONG_INPUT_DATA);
            return "form_enrollment";
        }

        if (checkUsernameDataBase(userName)) {
            UserAUX user = UserAUX.builder()
                    .userName(userName)
                    .password(password)
                    .firstName(firstName)
                    .lastName(lastName)
                    .email(email)
                    .phoneNumber(phoneNumber)
                    .build();
            userService.createUser(user, roleId);
            return "form_sussesfull";
        } else {
            model.addAttribute("usernameExists", true);
            return "form_enrollment";

        }
    }


    @GetMapping("/user/{username}")
    public UserAUX getUserByUserName(@PathVariable("username") String username) {
        return userService.getUserByUsername(username);
    }

    @GetMapping("/users")
    public List<UserAUX> getAllUsers() {

        return userService.getAllUsers();
    }

    @GetMapping("/user/{id}")
    public UserAUX getUserByID(@PathVariable("id") Long userId) {
        return userService.getUserById(userId);
    }


    private boolean checkDataFromForm(String line, String regex) {
        System.out.println(line);
        System.out.println(regex);
        return !line.matches(regex);
    }

    private boolean checkUsernameDataBase(String userName) {
        System.out.println(userName);
        try {
            userService.getUserByUsername(userName);
            return false;
        } catch (EntityNotFoundException e) {
            return true;
        }

    }
}
