package com.expertoption.expertoption.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterWorkerRequest {
    @NotBlank
    private String username;
    @NotNull
    private String workerKey;
    @NotBlank
    private String password;
}
