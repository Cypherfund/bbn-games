package com.cypherfund.bbn.controller;

import com.cypherfund.bbn.models.SettlementRequest;
import com.cypherfund.bbn.services.contract.ISettlementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/settlement/events")
@RequiredArgsConstructor
public class SettlementController {
    private final ISettlementService settlementService;

    @PostMapping("/winnings")
    public void processWinningsOdds(@RequestBody SettlementRequest settlementRequest) {
        settlementService.processWinningsOdds(settlementRequest.getEventId(), settlementRequest.getWinningOutcomeId());
        ResponseEntity.ok();
    }

    @PostMapping("/confirm")
    public void confirmWinnings(@RequestBody SettlementRequest settlementRequest) {
        settlementService.confirmWinnings(settlementRequest.getEventId(), settlementRequest.getWinningOutcomeId());
        ResponseEntity.ok();
    }

    @PostMapping("/{eventId}/void")
    public void handleEventVoid(@PathVariable Integer eventId) {
        settlementService.handleEventVoid(eventId);
        ResponseEntity.ok();
    }
}
