package ru.nextcloudnext.model;

public enum UserStatus {

    ACTIVE("Активен"),
    NOT_ACTIVE("Заблокирован");

    private final String statusName;

    UserStatus(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }
}

