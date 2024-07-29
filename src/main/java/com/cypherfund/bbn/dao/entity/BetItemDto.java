package com.cypherfund.bbn.dao.entity;

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
}
