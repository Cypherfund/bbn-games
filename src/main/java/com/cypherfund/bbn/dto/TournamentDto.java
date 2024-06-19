package com.cypherfund.bbn.dto;

import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link com.cypherfund.bbn.dao.entity.Tournament}
 */
@Data
public class TournamentDto implements Serializable {
    Long id;
    String name;
    LocalDate startDate;
    LocalDate endDate;

    Long categoryId;
}
