package com.cypherfund.bbn.dto;

import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.cypherfund.bbn.dao.entity.Category}
 */
@Data
public class CategoryDto implements Serializable {
    Long id;
    String name;
    Long gameId;
}
