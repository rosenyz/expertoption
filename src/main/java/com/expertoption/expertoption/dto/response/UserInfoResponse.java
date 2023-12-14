package com.expertoption.expertoption.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserInfoResponse {
    private Long id;
    private String username;
    private String email;
    private Boolean active;
    private Boolean verification;
    private String usedRefToken;
    private Double chance;
    private Double balance;
    private LocalDateTime dateOfCreate;
}
