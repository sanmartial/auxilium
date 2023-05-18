package com.globaroman.auxilium.service;

import com.globaroman.auxilium.IntegrationTestBase;
import com.globaroman.auxilium.model.entity.RoleAUX;
import com.globaroman.auxilium.model.entity.UserAUX;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest extends IntegrationTestBase {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testCreateUser() {
        //long user_id = userService.getAllUsers().stream().findFirst().orElseThrow().getId();
        //UserAUX userAUX = userService.getUserById(user_id);
        RoleAUX role = roleService.getAllRoles().stream().findFirst().orElseThrow();
        UserAUX userAUX = new UserAUX("Navi", "Navi1975", "Ivan", "Ivanov", "ivannavi@gmail.com", "067-424-88-33");

        UserAUX userBefore = userService.createUser(userAUX, role.getId());
        assertEquals(userAUX.getUserName(), userBefore.getUserName());

    }

    @Test
    void testGetUserById() {
        UserAUX userOld = userService.getAllUsers().stream().findFirst().orElseThrow();
        UserAUX user = userService.getUserById(userOld.getId());
        assertNotNull(user);
    }

    @Test
    void testGetUserByUsername() {
        UserAUX userOld = userService.getAllUsers().stream().findFirst().orElseThrow();
        String username = userService.getUserByUsername(userOld.getUserName()).getUserName();
        assertEquals(username, userOld.getUserName());
    }

    @Test
    void testGetUserByUsernameShouldThrowNull() {
        String username = "username";
        assertThrows(NullPointerException.class, () -> userService.getUserByUsername(username));


    }
}