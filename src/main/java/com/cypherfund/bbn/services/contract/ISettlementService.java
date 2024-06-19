package com.cypherfund.bbn.services.contract;

public interface ISettlementService {
    void processWinningsOdds(Integer eventId, Integer winningHousemateId);
    void confirmWinnings(Integer eventId, Integer winningHousemateId);

    void handleEventVoid(Integer eventId);
}
