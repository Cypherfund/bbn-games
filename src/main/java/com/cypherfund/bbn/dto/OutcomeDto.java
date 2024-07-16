package com.cypherfund.bbn.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link com.cypherfund.bbn.dao.entity.Outcome}
 */
@Data
public class OutcomeDto implements Serializable {
    Long id;
    @Size(max = 255)
    private String imgUrl;
    Integer eventId;
    String description;
    BigDecimal odds;
}
