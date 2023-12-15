package com.expertoption.expertoption.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BetInfoResponse {
    private Long id;
    private Double amountOfBet;
    private Boolean status;
    private Integer successBet;
    private Integer loseBet;
    private LocalDateTime dateOfCreateBet;
}
