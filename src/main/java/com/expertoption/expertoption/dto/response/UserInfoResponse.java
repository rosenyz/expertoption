package com.expertoption.expertoption.dto.response;

import com.expertoption.expertoption.model.Bet;
import com.expertoption.expertoption.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class UserInfoResponse {
    private Long id;
    private String username;
    private Boolean verification;
    private Double balance;
    private Integer successBet;
    private Integer loseBet;
    private Set<Role> roles;
    private LocalDateTime dateOfCreate;
}
