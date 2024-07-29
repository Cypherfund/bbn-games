package com.cypherfund.bbn.services.contract;

import com.cypherfund.bbn.dao.specifications.filters.BetFilterCriteria;
import com.cypherfund.bbn.dto.BetDto;
import com.cypherfund.bbn.dto.TicketDto;
import com.cypherfund.bbn.models.PredictionRequest;

import java.util.List;

public interface IBettingService {
    void placeBet(PredictionRequest predictionRequest);

    List<BetDto> getUserBets(BetFilterCriteria betFilterCriteria, int page, int size);
}
