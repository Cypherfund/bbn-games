package com.cypherfund.bbn.dao.specifications.filters;

import com.cypherfund.bbn.utils.Enumerations;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

/**
 * Author: E.Ngai
 * Date: 7/29/2024
 * Time: 2:12 PM
 **/
@Builder
@Getter
public class BetFilterCriteria {
    private String userId;
    private Instant startDate;
    private Instant endDate;
    private Integer jackpotId;
    Enumerations.BetType betType;
    Enumerations.BetStatus status;
}
