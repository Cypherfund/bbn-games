package com.cypherfund.bbn.dto;

import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link com.cypherfund.bbn.dao.entity.Outcome}
 */
@Value
public class OutcomeDto implements Serializable {
    Long id;
    Integer eventId;
    String description;
    BigDecimal odds;
}