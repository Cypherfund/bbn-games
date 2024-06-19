package com.cypherfund.bbn.dto;

import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link com.cypherfund.bbn.dao.entity.Tournament}
 */
@Value
public class TournamentDto implements Serializable {
    Long id;
    String name;
    LocalDate startDate;
    LocalDate endDate;

    Long categoryId;
}