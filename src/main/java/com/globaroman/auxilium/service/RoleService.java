package com.globaroman.auxilium.service;

import com.globaroman.auxilium.model.entity.RoleAUX;
import com.globaroman.auxilium.model.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository repository;

    @Autowired
    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    public RoleAUX getRoleById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Role_not_found"));
    }

    public List<RoleAUX> getAllRoles() {
        return repository.findAll();
    }


}
