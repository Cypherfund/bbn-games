package com.cypherfund.bbn.services.contract;

import com.cypherfund.bbn.models.PredictionRequest;

public interface IBettingService {
    void placeBet(PredictionRequest predictionRequest);
}
