package com.cypherfund.bbn.models;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SettlementRequest {
    @NotNull
    private Integer eventId;
    @NotNull
    private Integer winningOutcomeId;
}
