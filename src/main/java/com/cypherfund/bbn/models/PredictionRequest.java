package com.cypherfund.bbn.models;

import com.cypherfund.bbn.utils.Enumerations.TicketType;
import com.cypherfund.bbn.utils.Enumerations.BetType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PredictionRequest {
    private String userId;
    private TicketType ticketType;
    private List<Bet> bets;

    @Data
    public static class Bet {
        private BetType betType;
        private BigDecimal amount;
        private List<Event> events;

        @Data
        public static class Event {
            private Long eventId;
            private String prediction;
            private BigDecimal odds;
        }
    }
}
