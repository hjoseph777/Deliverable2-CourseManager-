package com.cpan228.cms.model;

import org.springframework.security.crypto.password.PasswordEncoder;

public class RegistrationForm {
    private String username;
    private String password;
    private String role; // "ADMIN", "INSTRUCTOR", "STUDENT"

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public User toUser(PasswordEncoder passwordEncoder) {
        Role userRole = Role.ROLE_STUDENT;
        if (role != null) {
            try {
                userRole = Role.valueOf("ROLE_" + role.toUpperCase());
            } catch (IllegalArgumentException e) {
                 userRole = Role.ROLE_STUDENT;
            }
        }
        return new User(username, passwordEncoder.encode(password), userRole);
    }
}
