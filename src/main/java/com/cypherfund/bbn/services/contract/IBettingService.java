package com.cypherfund.bbn.services.contract;

import com.cypherfund.bbn.dto.TicketDto;
import com.cypherfund.bbn.models.PredictionRequest;

import java.util.List;

public interface IBettingService {
    void placeBet(PredictionRequest predictionRequest);

    List<TicketDto> getUserTickets(String userId);
}
