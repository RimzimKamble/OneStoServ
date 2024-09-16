package com.example.OneStopServ.dto;

import com.example.OneStopServ.enums.UserRole;
import lombok.Getter;

@Getter
public class AuthenticationRequestDTO {
    public AuthenticationRequestDTO(Long id, UserRole role, String s) {
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    private Long userId;

    public void setToken(String token) {
        this.token = token;
    }


    public void setRole(UserRole role) {
        this.role = role;
    }

    private UserRole role;
    private String token;

//    public void AuthenticationResponseDTO(Long userId, String role, String token) {
//        this.userId = userId;
//        this.role = role;
//        this.token = token;
//    }

    public void AuthenticationResponseDTO(Long userId, UserRole role, String token) {
        this.userId = userId;
        this.role = role;
        this.token = token;
    }

}
