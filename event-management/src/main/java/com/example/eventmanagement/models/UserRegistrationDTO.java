package com.example.eventmanagement.models;

import com.example.eventmanagement.enums.UserRole;
import lombok.Data;

@Data
public class UserRegistrationDTO {
    private String name;
    private String email;
    private String password;
    private UserRole role;
}
