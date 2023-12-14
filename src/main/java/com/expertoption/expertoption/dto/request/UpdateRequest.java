package com.expertoption.expertoption.dto.request;

import lombok.Data;

@Data
public class UpdateRequest {
    private Boolean active;
    private Boolean verification;
    private Double chance;
    private Double balance;
}
