package com.univercity.vntu.auth;

public enum ApplicationUserPermission {
    STUDENT_READ("student:read"),
    STUDENT_WRITE("student:write"),
    TEST_READ("test:read"),
    TEST_WRITE("test:write"),
    TEACHER_READ("teacher:read"),
    TEACHER_WRITE("teacher:write");

    private final String permissions;

    ApplicationUserPermission(String permissions) {
        this.permissions = permissions;
    }

    public String getPermissions(){
        return permissions;
    }
}
