package com.cypherfund.bbn.controller;


import com.cypherfund.bbn.dto.TicketDto;
import com.cypherfund.bbn.models.PredictionRequest;
import com.cypherfund.bbn.services.contract.IBettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bets")
@RequiredArgsConstructor
public class PredictionController {
    private final IBettingService bettingService;

    @PostMapping
    public void placebet(@RequestBody PredictionRequest predictionRequest) {
        bettingService.placeBet(predictionRequest);
    }

    @GetMapping("/tickets/{userId}")
    public List<TicketDto> getUserTickets(@PathVariable String userId) {
        return bettingService.getUserTickets(userId);
    }
}
