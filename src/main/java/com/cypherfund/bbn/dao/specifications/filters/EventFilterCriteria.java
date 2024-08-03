package com.cypherfund.bbn.dao.specifications.filters;

import com.cypherfund.bbn.utils.Enumerations;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EventFilterCriteria {
    Enumerations.Event_Status status;
    long tournamentId;
}
