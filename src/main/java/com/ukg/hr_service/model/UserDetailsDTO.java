package com.ukg.hr_service.model;

import java.util.List;

import lombok.Data;

@Data
public class UserDetailsDTO {
    private String email;
    private boolean authenticated;
    private List<String> authorities;

    // All-args constructor
    public UserDetailsDTO(String email, boolean authenticated, List<String> authorities) {
        this.email = email;
        this.authenticated = authenticated;
        this.authorities = authorities;
    }
}
