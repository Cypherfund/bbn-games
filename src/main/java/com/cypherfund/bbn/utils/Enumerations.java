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
        NOMINATIONS, EVICTIONS, TASKS, WINNER, HEAD_OF_HOUSE, VETO_POWER,
        NOMINATION_REPLACEMENT, VETO_POWER_REPLACEMENT, DISQUALIFICATION, EVICTION_REPLACEMENT
    }

    public enum GAME_STATUS {
        ACTIVE, INACTIVE
    }


    public enum USER_STATUS {
        active, blocked, suspended, disabled
    }

    public enum TRANSACTION_TYPE {
        BB_BET, BB_WINNING, WITHDRAWAL, DEPOSIT, SUBSCRIPTION
    }
}
