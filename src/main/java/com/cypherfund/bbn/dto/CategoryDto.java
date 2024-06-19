package com.cypherfund.bbn.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.cypherfund.bbn.dao.entity.Category}
 */
@Value
public class CategoryDto implements Serializable {
    Long id;
    String name;
    Long gameId;
}