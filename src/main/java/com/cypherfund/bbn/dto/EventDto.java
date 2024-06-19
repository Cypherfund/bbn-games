package com.cypherfund.bbn.dto;

import com.cypherfund.bbn.dao.entity.Event;
import com.cypherfund.bbn.utils.Enumerations;
import lombok.Value;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link Event}
 */
@Value
public class EventDto implements Serializable {
    Integer id;
    String name;
    Enumerations.EVENT_TYPE type;
    String outcome;
    Enumerations.Event_Status status;
    Instant createdAt;
    Instant updatedAt;
    Instant eventDate;
    Long tournamentId;
}