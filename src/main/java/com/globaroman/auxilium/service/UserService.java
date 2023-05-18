package com.globaroman.auxilium.service;

import com.globaroman.auxilium.model.entity.RoleAUX;
import com.globaroman.auxilium.model.entity.UserAUX;
import com.globaroman.auxilium.model.repository.RoleRepository;
import com.globaroman.auxilium.model.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    @Autowired
    public UserService(UserRepository userRepository, RoleRepository repository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;

    }

    public UserAUX createUser(UserAUX user, Long roleId) {

        RoleAUX role = roleService.getRoleById(roleId);
        role.addListRoles(user);
        user.setRole(role);
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
