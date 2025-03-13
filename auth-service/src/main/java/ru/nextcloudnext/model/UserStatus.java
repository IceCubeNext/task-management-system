package ru.nextcloudnext.model;

/**
 * @author Vladimir F. 22.03.2024 12:46
 */
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

