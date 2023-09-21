package com.globaroman.auxilium.service;

import com.globaroman.auxilium.IntegrationTestBase;
import com.globaroman.auxilium.model.entity.UserAUX;
import com.globaroman.auxilium.model.entity.security.RoleAUX;
import com.globaroman.auxilium.model.entity.security.Status;
import jakarta.persistence.EntityNotFoundException;
import org.hamcrest.MatcherAssert;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest extends IntegrationTestBase {
    @Autowired
    private UserService userService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    UserAUX createNewUserAUX() {
        return new UserAUX("Navi",
                "Navi1975",
                "Ivan",
                "Ivanov",
                "ivannavi@gmail.com",
                "067-424-88-33",
                RoleAUX.PATIENT,
                Status.ACTIVE);
    }
    @Test
    void testCreateUser() {
        UserAUX userAUX = createNewUserAUX();
        UserAUX userBefore = userService.createUser(userAUX);
        assertEquals(userAUX.getUsername(), userBefore.getUsername());

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
        String username = userService.getUserByUsername(userOld.getUsername()).getUsername();
        assertEquals(username, userOld.getUsername());
    }

    @Test
    void testGetUserByUsernameShouldThrowNull() {
        String username = "username234";
        assertThrows(EntityNotFoundException.class, () -> userService.getUserByUsername(username));


    }

     @Test
    void getAllUsers() {
         List<UserAUX> list = userService.getAllUsers();
         Integer countPeople = userService.countAllPeople();
         MatcherAssert.assertThat(list, IsCollectionWithSize.hasSize(countPeople));    }




    @Test

    void getAllUsersByPatient() {
        List<UserAUX> listAll = userService.getAllUsersByPatient();
        assertFalse(listAll.isEmpty());
    }

    @Test
    void countAllPeople() {
        assertTrue(userService.countAllPeople() > 0);
    }
}