package com.cypherfund.bbn.dto;

import lombok.Value;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link com.cypherfund.bbn.dao.entity.Housemate}
 */
@Value
public class HousemateDto implements Serializable {
    Integer id;
    String name;
    String bio;
    String status;
    String imageUrl;
    Instant createdAt;
    Instant updatedAt;
}