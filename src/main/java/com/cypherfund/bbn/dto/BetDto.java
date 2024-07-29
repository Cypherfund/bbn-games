package com.cypherfund.bbn.dto;

import com.cypherfund.bbn.dao.entity.Bet;
import com.cypherfund.bbn.utils.Enumerations;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/**
 * DTO for {@link Bet}
 */
@Value
@Builder
public class BetDto implements Serializable {
    Long id;
    Enumerations.BetType betType;
    Enumerations.BetStatus status;
    Instant createdAt;
    Instant updatedAt;
    BigDecimal potentialWinnings;
    BigDecimal taxAmount;
    BigDecimal finalWinnings;
    BigDecimal amount;
    List<BetItemDto> betItems;
}
