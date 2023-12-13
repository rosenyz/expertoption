package com.expertoption.expertoption.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank
    private String username;
    @Email
    private String email;
    @NotBlank
    private String password;
}
