package com.dailycode.user.management.constants;

public enum StatusEnum {
    ACTIVE("Active"),
    IN_ACTIVE("In Active");

    private final String status;

    StatusEnum(String status) {
        this.status = status;
    }

    public String value() {
        return this.status;
    }
}
