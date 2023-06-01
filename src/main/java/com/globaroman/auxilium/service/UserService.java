package com.globaroman.auxilium.service;

import com.globaroman.auxilium.model.entity.RoleAUX;
import com.globaroman.auxilium.model.entity.UserAUX;
import com.globaroman.auxilium.model.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository ) {
        this.userRepository = userRepository;
    }

    public UserAUX createUser(UserAUX user) {
         return userRepository.save(user);
    }

    public List<UserAUX> getAllUsers() {
        return userRepository.findAll();
    }

    public UserAUX getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->new EntityNotFoundException("user_not_found"));
    }

    public UserAUX getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

}
