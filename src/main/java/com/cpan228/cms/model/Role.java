package com.cpan228.cms.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN, ROLE_INSTRUCTOR, ROLE_STUDENT;

    @Override
    public String getAuthority() {
        return name();
    }
}
