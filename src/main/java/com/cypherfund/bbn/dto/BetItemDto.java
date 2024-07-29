package com.cypherfund.bbn.dto;

import com.cypherfund.bbn.dao.entity.BetItem;
import com.cypherfund.bbn.utils.Enumerations;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link BetItem}
 */
@Value
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class BetItemDto implements Serializable {
    Long id;
    Long event;
    BigDecimal odds;
    Enumerations.BetStatus status;
    Long outcomeId;
    String outcomeName;
    String eventName;

    public BetItemDto(Long id, Long event, BigDecimal odds, Enumerations.BetStatus status, Long outcomeId, String outcomeName, String eventName) {
        this.id = id;
        this.event = event;
        this.odds = odds;
        this.status = status;
        this.outcomeId = outcomeId;
        this.outcomeName = outcomeName;
        this.eventName = eventName;
    }
}
