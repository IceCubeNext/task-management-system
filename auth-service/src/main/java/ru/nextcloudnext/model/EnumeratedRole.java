package ru.nextcloudnext.model;

import org.springframework.security.core.GrantedAuthority;

public enum EnumeratedRole implements GrantedAuthority {

    ROLE_ADMIN("Администратор"),
    ROLE_USER("Пользователь");

    private final String roleFullName;

    EnumeratedRole(String roleFullName) {
        this.roleFullName = roleFullName;
    }

    public String getRoleFullName() {
        return roleFullName;
    }

    @Override
    public String getAuthority() {
        return name();
    }
}
