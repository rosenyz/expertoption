package com.expertoption.expertoption.dto.request;

import lombok.Data;

@Data
public class UpdateRequest {
    private Boolean active;
    private Boolean verification;
    private Boolean isWithdrawAvailable;
    private Double chance;
    private Double balance;
    private String usedRefToken;
}
