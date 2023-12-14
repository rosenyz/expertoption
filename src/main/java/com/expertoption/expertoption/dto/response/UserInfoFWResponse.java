package com.expertoption.expertoption.dto.response;

import com.expertoption.expertoption.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
public class UserInfoFWResponse {
    private Long id;
    private String username;
    private String email;
    private Boolean active;
    private Boolean verification;
    private Boolean isWithdrawAvailable;
    private String usedRefToken;
    private Double chance;
    private Double balance;
    private Set<Role> roles;
    private LocalDateTime dateOfCreate;
}
