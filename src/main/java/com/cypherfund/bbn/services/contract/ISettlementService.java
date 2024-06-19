package com.cypherfund.bbn.services.contract;

public interface ISettlementService {
    void processWinningsOdds(Integer eventId, Integer winningOutcomeId);
    void confirmWinnings(Integer eventId, Integer winningOutcomeId);

    void handleEventVoid(Integer eventId);
}
