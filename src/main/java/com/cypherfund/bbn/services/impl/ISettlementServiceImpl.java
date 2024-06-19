package com.cypherfund.bbn.services.impl;

import com.cypherfund.bbn.dao.entity.*;
import com.cypherfund.bbn.dao.repository.*;
import com.cypherfund.bbn.exception.AppException;
import com.cypherfund.bbn.services.contract.ISettlementService;
import com.cypherfund.bbn.utils.Enumerations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

import static com.cypherfund.bbn.utils.Enumerations.BetStatus.*;
import static com.cypherfund.bbn.utils.Enumerations.Event_Status.SETTLED;

@Slf4j
@Service
@RequiredArgsConstructor
public class ISettlementServiceImpl implements ISettlementService {
    private final BetRepository betRepository;
    private final BetItemRepository betItemRepository;
    private final AuditLogRepository auditLogRepository;
    private final HousemateRepository housemateRepository;
    private final EventRepository eventRepository;
    private final WinningTicketRepository winningTicketRepository;
    private final JackpotEventRepository jackpotEventRepository;
    private final JackpotRepository jackpotRepository;
    private static final BigDecimal TAX_RATE = new BigDecimal("0.1"); // 10% tax

    @Override
    @Transactional
    public void processWinningsOdds(Integer eventId, Integer winningOutcomeId) {
        log.info("Processing winnings for event {}", eventId);
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new AppException("Event not found"));
        housemateRepository.findById(winningOutcomeId).orElseThrow(() -> new AppException("Housemate not found"));

        event.setOutcome(winningOutcomeId.toString());
        event.setStatus(SETTLED);

        eventRepository.save(event);

        List<BetItem> betItems = settleBetItems(eventId, winningOutcomeId);

        List<Bet> bets = betItems.stream()
                .map(BetItem::getBet)
                .distinct()
                .filter(bet -> bet.getTicket().getType().equals(Enumerations.TicketType.ODDS))
                .toList();

        settleOddsTickets(bets);

        settleJackpotEvents(eventId, event.getStatus());

    }

    @Override
    @Transactional
    public void handleEventVoid(Integer eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new AppException("Event not found"));
        event.setStatus(Enumerations.Event_Status.CANCELLED);

        List<BetItem> betItems = betItemRepository.findByEvent(eventId);
        List<BetItem> betItemsToVoid = new ArrayList<>();
        List<Bet> betsToRecalculate = new ArrayList<>();

        for (BetItem betItem : betItems) {
            betItem.setOdds(BigDecimal.ONE);
            betItem.setStatus(CANCELLED);
            betItemsToVoid.add(betItem);

            Bet bet = betItem.getBet();
            if (bet.getTicket().getType().equals(Enumerations.TicketType.ODDS)) {
                BigDecimal oldWinnings = bet.getFinalWinnings();
                bet.setPotentialWinnings(recalculateBetWinnings(bet, betItem));
                bet.setTaxAmount(calculateTax(bet.getPotentialWinnings()));
                bet.setFinalWinnings(bet.getPotentialWinnings().subtract(bet.getTaxAmount()));
                betsToRecalculate.add(bet);

                createAuditLog(event.getId().longValue(), "EVENT_VOID", BigDecimal.ONE, BigDecimal.ZERO, "event voided");
                createAuditLog(bet.getId(), "BET_RECALCULATION", oldWinnings, bet.getFinalWinnings(), "bet winnings recalculated");
                createAuditLog(bet.getId(), "BET_RECALCULATION_ODD", BigDecimal.ONE, betItem.getOdds(), "bet odds recalculated");
            }
        }

        eventRepository.save(event);
        betItemRepository.saveAll(betItemsToVoid);
        betRepository.saveAll(betsToRecalculate);

        this.settleJackpotEvents(eventId, Enumerations.Event_Status.CANCELLED);

    }

    @Override
    public void confirmWinnings(Integer eventId, Integer winningOutcomeId) {
//        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new AppException("Ticket not found"));
//        if ("won".equals(ticket.getStatus())) {
//            WinningTicket winningTicket = winningTicketRepository.findByTicketId(ticketId)
//                    .orElseThrow(() -> new AppException("Winning ticket not found"));
//            User user = userRepository.findById(ticket.getUserId()).orElseThrow(() -> new AppException("User not found"));
//            user.setBalance(user.getBalance().add(winningTicket.getFinalWinnings()));
//            userRepository.save(user);
//
//            ticket.setStatus("credited");
//            ticketRepository.save(ticket);
//        }
    }

    //<editor-fold desc="[ PRIVATE METHODS ]" defaultstate="collapse">
    private void settleJackpotEvents(Integer eventId, Enumerations.Event_Status status) {
        //check if event is part of jackpot and update status of jackpot event
        List<JackpotEvent> jackpotEvents = jackpotEventRepository.findByEvent_Id(eventId);
        List<JackpotEvent> jackpotEventsToSave = new ArrayList<>();
        for (JackpotEvent jackpotEvent : jackpotEvents) {
            jackpotEvent.setStatus(status);
            jackpotEvents.add(jackpotEvent);
        }

        jackpotEventRepository.saveAll(jackpotEventsToSave);

        //find jackpots with all assigned events settled
        List<Jackpot> jackpots = jackpotEvents.stream().map(JackpotEvent::getJackpot).distinct().toList();
        List<Jackpot> jackpotsToSave = new ArrayList<>();
        for (Jackpot jackpot : jackpots) {
            Set<JackpotEvent> jackpotEventsForJackpot = jackpot.getJackpotEvents();
            boolean allEventsSettled = jackpotEventsForJackpot.stream()
                    .map(JackpotEvent::getEvent)
                    .allMatch(jackpotEvent -> SETTLED.equals(jackpotEvent.getStatus()) || Enumerations.Event_Status.CANCELLED.equals(jackpotEvent.getStatus()));
            if (allEventsSettled) {
                jackpot.setStatus(SETTLED.name());
                jackpotsToSave.add(jackpot);
            }

            //todo call for calculation of jackpot winnings
        }

        jackpotRepository.saveAll(jackpotsToSave);
    }

    private void settleOddsTickets(List<Bet> bets) {
        List<Bet> betsToSave = new ArrayList<>();

        for (Bet bet : bets) {
            Set<BetItem> betItemsForBet = bet.getBetItems();
            boolean allItemsSettled = betItemsForBet.stream().allMatch(item -> LOST.equals(item.getStatus()) || WON.equals(item.getStatus()));
            if (allItemsSettled) {
                boolean allItemsWon = betItemsForBet.stream().allMatch(item -> WON.equals(item.getStatus()));
                bet.setStatus(allItemsWon ? WON : LOST);
                betsToSave.add(bet);
                createWinningTicket(bet);
            }
        }

        betRepository.saveAll(betsToSave);
    }

    private List<BetItem> settleBetItems(Integer eventId, Integer winningHousemateId) {
        List<BetItem> betItems = betItemRepository.findByEvent(eventId);
        for (BetItem betItem : betItems) {
            if (betItem.getOutcomeId().intValue() == winningHousemateId) {
                betItem.setStatus(WON);
            } else {
                betItem.setStatus(LOST);
            }
        }
        betItemRepository.saveAll(betItems);
        return betItems;
    }

    private void createWinningTicket(Bet bet) {
        WinningTicket winningTicket = new WinningTicket();
        winningTicket.setBet(bet);
        winningTicket.setUserId(bet.getTicket().getUserId());
        winningTicket.setTotalWinnings(bet.getPotentialWinnings());
        winningTicket.setTaxAmount(bet.getTaxAmount());
        winningTicket.setFinalWinnings(bet.getFinalWinnings());
        winningTicket.setCreatedAt(Instant.now());

        winningTicketRepository.save(winningTicket);
    }


    private void createAuditLog(Long betId, String actionType, BigDecimal oldValue, BigDecimal newValue, String message){
        AuditLog auditLog = new AuditLog();
        auditLog.setEntityId(betId.toString());
        auditLog.setEntityType("Bet");
        auditLog.setMessage(message);
        auditLog.setActionType(actionType);
        auditLog.setOldValue(oldValue);
        auditLog.setNewValue(newValue);
        auditLog.setCreatedAt(Instant.now());
        auditLogRepository.save(auditLog);
    }

    private BigDecimal recalculateBetWinnings(Bet bet, BetItem voidedBetItem) {
        Set<BetItem> betItems = bet.getBetItems();
        BigDecimal betOdds = BigDecimal.ONE;

        for (BetItem betItem : betItems) {
            if (betItem.getId().equals(voidedBetItem.getId())) {
                betItem.setOdds(BigDecimal.ONE);
            }

            betOdds = betOdds.multiply(betItem.getOdds());
        }

        return bet.getAmount().multiply(betOdds);
    }

    private BigDecimal calculateTax(BigDecimal amount) {
        return amount.multiply(TAX_RATE);
    }
    //</editor-fold>


}
