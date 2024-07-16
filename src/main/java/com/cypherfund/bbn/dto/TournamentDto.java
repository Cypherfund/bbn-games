package com.cypherfund.bbn.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link com.cypherfund.bbn.dao.entity.Tournament}
 */
@Data
public class TournamentDto implements Serializable {
    @Size(max = 255)
    private String imgUrl;
    Long id;
    String name;
    LocalDate startDate;
    LocalDate endDate;

    Long categoryId;
}
