package com.cypherfund.bbn.dao.specifications.filters;

import com.cypherfund.bbn.utils.Enumerations;
import lombok.Builder;
import lombok.Getter;

/**
 * Author: E.Ngai
 * Date: 7/29/2024
 * Time: 2:12 PM
 **/
@Builder
@Getter
public class BetFilterCriteria {
    private String userId;
    private String startDate;
    private String endDate;
    private Integer jackpotId;
    Enumerations.BetType betType;
    Enumerations.BetStatus status;
}
