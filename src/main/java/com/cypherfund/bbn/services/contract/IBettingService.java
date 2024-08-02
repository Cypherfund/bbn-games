package com.cypherfund.bbn.services.contract;

import com.cypherfund.bbn.dao.specifications.filters.BetFilterCriteria;
import com.cypherfund.bbn.dto.BetDto;
import com.cypherfund.bbn.dto.TicketDto;
import com.cypherfund.bbn.models.ApiResponse;
import com.cypherfund.bbn.models.PredictionRequest;
import com.cypherfund.bbn.utils.CustomPage;

import java.util.List;

public interface IBettingService {
    void placeBet(PredictionRequest predictionRequest);

    ApiResponse<List<BetDto>> getUserBets(BetFilterCriteria betFilterCriteria, int page, int size);
}
