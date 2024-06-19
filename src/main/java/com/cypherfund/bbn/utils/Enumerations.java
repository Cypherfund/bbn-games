package com.cypherfund.bbn.utils;

public class Enumerations {
    public enum BetType {
        SINGLE, COMBINATION
    }

    public enum BetStatus {
        PENDING, WON, LOST, CANCELLED
    }

    public enum SettlementStatus {
        PENDING, CONFIRMED
    }

    public enum TicketType {
        ODDS, JACKPOT
    }

    public enum TicketStatus {
        PENDING, WON, LOST, CANCELLED
    }

    public enum Event_Status {
        SETTLED, PENDING, CANCELLED
    }

    public enum Jackpot_Status {
        PENDING, SETTLED, DISBURSED, CANCELLED
    }

    public enum EVENT_TYPE {
        NOMINATIONS, EVICTIONS, TASKS, WINNER, HEAD_OF_HOUSEHOLD, VETO_POWER, NOMINATION_REPLACEMENT, VETO_POWER_REPLACEMENT
    }

}
