package com.cypherfund.bbn.dto;

import com.cypherfund.bbn.utils.Enumerations;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/**
 * DTO for {@link com.cypherfund.bbn.dao.entity.Ticket}
 */
@Value
@Builder
public class TicketDto implements Serializable {
    Long id;
    String userId;
    Enumerations.TicketType type;
    BigDecimal totalAmount;
    BigDecimal totalOdds;
    Enumerations.TicketStatus status;
    Integer correctPredictions;
    Instant createdAt;
    Instant updatedAt;
    List<BetDto> bets;
}