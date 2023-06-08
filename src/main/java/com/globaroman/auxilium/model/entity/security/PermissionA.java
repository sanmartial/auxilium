package com.globaroman.auxilium.model.entity.security;

public enum PermissionA {
    USER_CREATE("user:create"),
    USER_READ("user:read"),
    USER_WRITE("user:write"),
    USER_UPDATE("user:update");
    private final String permission;

    PermissionA(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
