package com.cypherfund.bbn.services.impl;

import com.cypherfund.bbn.dao.entity.*;
import com.cypherfund.bbn.dao.repository.*;
import com.cypherfund.bbn.utils.Enumerations;
import com.cypherfund.bbn.exception.AppException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class JackpotService {
    private final JackpotRepository jackpotRepository;
    private final WinningTicketRepository winningTicketRepository;
    private final JackpotRuleRepository jackpotRuleRepository;
    private final BetRepository betRepository;

    @Transactional
    public void settleJackpot(Integer jackpotId) {
        Jackpot jackpot = jackpotRepository.findById(jackpotId).orElseThrow(() -> new AppException("Jackpot not found"));
        calculateJackpotWinnings(jackpot);
    }

    private void calculateJackpotWinnings(Jackpot jackpot) {
        //sum total stake from bets with jackpot id
        BigDecimal totalStake = betRepository.findByJackpotId(jackpot.getId()).stream()
                .map(Bet::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int numberOfVoidedJackpotEvents = jackpot.getJackpotEvents().stream()
                .filter(event -> Enumerations.Event_Status.CANCELLED.equals(event.getStatus()))
                .toList().size();

        BigDecimal remainingAmount = totalStake;

        List<JackpotRule> jackpotRules = jackpotRuleRepository.findAllByJackpotTypeAndVoidedEventsAllowed(jackpot.getJackpotType(), numberOfVoidedJackpotEvents);
        Map<Integer, List<Bet>> groupedTickets = groupBetsByCorrectPredictions(jackpot);

        for (JackpotRule rule : jackpotRules) {
            BigDecimal prizeAmount = totalStake.multiply(rule.getPrizePercentage().divide(BigDecimal.valueOf(100), RoundingMode.DOWN));
            remainingAmount = remainingAmount.subtract(prizeAmount);

            List<Bet> winningBets = groupedTickets.getOrDefault(rule.getCorrectPredictions(), Collections.emptyList());
            distributeWinnings(winningBets, prizeAmount);
        }

        log.info("Remaining amount: {}", remainingAmount);

        jackpot.setStatus(Enumerations.SettlementStatus.CONFIRMED.name());
        jackpotRepository.save(jackpot);
    }

    private Map<Integer, List<Bet>> groupBetsByCorrectPredictions(Jackpot jackpot) {
        Map<Integer, List<Bet>> groupedTickets = new HashMap<>();
        List<Bet> bets = betRepository.findByJackpotId(jackpot.getId());

        for (Bet bet : bets) {
            int correctPredictions = calculateCorrectPredictions(bet);
            groupedTickets.computeIfAbsent(correctPredictions, k -> new ArrayList<>()).add(bet);
        }

        return groupedTickets;
    }

    private int calculateCorrectPredictions(Bet bet) {
        int correctPredictions = 0;
        //calculate correct predictions from bet items
        Set<BetItem> betItems = bet.getBetItems();
        for (BetItem betItem : betItems) {
            if (Enumerations.BetStatus.WON.equals(betItem.getStatus())
                    || Enumerations.BetStatus.CANCELLED.equals(betItem.getStatus())) {
                correctPredictions++;
            }
        }

        return correctPredictions;
    }

    private void distributeWinnings(List<Bet> winningBets, BigDecimal prizeAmount) {
        BigDecimal amountPerTicket = prizeAmount.divide(BigDecimal.valueOf(winningBets.size()), RoundingMode.DOWN);
        BigDecimal taxAmount = amountPerTicket.multiply(BigDecimal.valueOf(0.1));
        BigDecimal finalWinnings = amountPerTicket.subtract(taxAmount);

        List<WinningTicket> winningTickets = new ArrayList<>();
        for (Bet bet : winningBets) {
            bet.setPotentialWinnings(amountPerTicket);
            bet.setTaxAmount(amountPerTicket.multiply(BigDecimal.valueOf(0.2)));
            bet.setFinalWinnings(amountPerTicket.subtract(bet.getTaxAmount()));
            WinningTicket winningTicket = buildWinningTicket(amountPerTicket, taxAmount, finalWinnings, bet);
            winningTickets.add(winningTicket);
        }

        winningTicketRepository.saveAll(winningTickets);
    }

    private WinningTicket buildWinningTicket(BigDecimal winningAmount, BigDecimal taxAmount, BigDecimal finalWinnings, Bet bet) {
        WinningTicket winningTicket = new WinningTicket();
        winningTicket.setBet(bet);
        winningTicket.setUserId(bet.getTicket().getUserId());
        winningTicket.setTotalWinnings(winningAmount);
        winningTicket.setTaxAmount(taxAmount);
        winningTicket.setFinalWinnings(finalWinnings);
        winningTicket.setCreatedAt(Instant.now());

        return winningTicket;
    }
}
