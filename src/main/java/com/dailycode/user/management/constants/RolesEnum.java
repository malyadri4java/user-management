package com.dailycode.user.management.constants;

public enum RolesEnum {
    ADMIN("Admin"),
    ENGINEER("Engineer");

    private String role;

    RolesEnum(String role) {
        this.role = role;
    }

    public String value() {
        return this.role;
    }

}
