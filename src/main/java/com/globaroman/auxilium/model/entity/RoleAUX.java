package com.globaroman.auxilium.model.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum RoleAUX implements GrantedAuthority {
    PATIENT(Set.of(PermissionA.USER_READ)),
    DOCTOR(Set.of(PermissionA.USER_READ, PermissionA.USER_WRITE)),
    ADMIN(Set.of(PermissionA.USER_READ, PermissionA.USER_WRITE));
    private final Set<PermissionA> permission;

    RoleAUX(Set<PermissionA> permission) {
        this.permission = permission;
    }

    @Override
    public String getAuthority() {
        return name();
    }

    public Set<PermissionA> getPermission() {
        return permission;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermission().stream()
                .map(permissionA -> new SimpleGrantedAuthority(permissionA.getPermission()))
                .collect(Collectors.toSet());
    }
}
