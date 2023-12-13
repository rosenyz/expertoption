package com.expertoption.expertoption.model.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_USER, ROLE_WORKER, ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
