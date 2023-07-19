package com.globaroman.auxilium.controller;

import com.globaroman.auxilium.model.entity.UserAUX;
import com.globaroman.auxilium.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {
    private final UserService userService;

    @GetMapping("/username/{username}")
    public UserAUX getUserByUserName(@PathVariable("username") String username) {
        return userService.getUserByUsername(username);
    }

    @GetMapping("/all")
    public List<UserAUX> getAllUsers() {

        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserAUX getUserByID(@PathVariable("id") Long userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/patients")
    public List<UserAUX> getListPatients() {
        return userService.getAllUsersByPatient();
    }

}
