package com.mike.projects.fitsynergy.security.model;

public enum SystemUserPermission {
    USER_FETCH("user:get"),
    USERS_FETCH("users:get"),
    USER_CREATE("user:save"),
    USER_UPDATE("user:update"),
    USER_DELETE("user:delete"),

    PROGRAM_FETCH("program:get"),
    PROGRAMS_FETCH("programs:get"),
    PROGRAM_CREATE("program:save"),
    PROGRAM_UPDATE("program:update"),
    PROGRAM_DELETE("program:delete");

    private final String permission;

    SystemUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
