package com.cypherfund.bbn.services.impl;

import com.cypherfund.bbn.dao.entity.Bet;
import com.cypherfund.bbn.dao.entity.BetItem;
import com.cypherfund.bbn.dao.entity.BetItemDto;
import com.cypherfund.bbn.dao.entity.Ticket;
import com.cypherfund.bbn.dao.repository.BetItemRepository;
import com.cypherfund.bbn.dao.repository.BetRepository;
import com.cypherfund.bbn.dao.repository.TicketRepository;
import com.cypherfund.bbn.dao.specifications.BetSearchSpecification;
import com.cypherfund.bbn.dao.specifications.filters.BetFilterCriteria;
import com.cypherfund.bbn.dto.BetDto;
import com.cypherfund.bbn.dto.TicketDto;
import com.cypherfund.bbn.exception.AppException;
import com.cypherfund.bbn.models.ApiResponse;
import com.cypherfund.bbn.models.DebitRequest;
import com.cypherfund.bbn.models.PredictionRequest;
import com.cypherfund.bbn.proxies.UserFeignClient;
import com.cypherfund.bbn.services.contract.IBettingService;
import com.cypherfund.bbn.utils.Enumerations;
import com.cypherfund.bbn.utils.Enumerations.BetStatus;
import com.cypherfund.bbn.utils.Enumerations.TicketStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.cypherfund.bbn.utils.Enumerations.TicketType.ODDS;

@Service
@RequiredArgsConstructor
public class IBettingServiceImpl implements IBettingService {
    private final TicketRepository ticketRepository;
    private final BetRepository betRepository;
    private final BetItemRepository betItemRepository;
    private final UserFeignClient userFeignClient;
    private static final BigDecimal TAX_RATE = new BigDecimal("0.1"); // 10% tax

    @Override
    @Transactional
    public void placeBet(PredictionRequest predictionRequest) {
        // Create a new ticket
        Ticket ticket = new Ticket();
        ticket.setUserId(predictionRequest.getUserId());
        ticket.setType(predictionRequest.getTicketType());
        ticket.setTotalAmount(calculateTotalAmount(predictionRequest.getBets()));
        ticket.setTotalOdds(calculateTotalOdds(predictionRequest.getBets()));
        ticket.setStatus(TicketStatus.PENDING);
        ticket.setCreatedAt(Instant.now());
        ticket.setCorrectPredictions(0);
        ticket = ticketRepository.save(ticket);

        // Create bets and bet items
        for (PredictionRequest.Bet bet : predictionRequest.getBets()) {
            Bet newBet = new Bet();
            newBet.setTicket(ticket);
            newBet.setBetType(bet.getBetType());
            newBet.setStatus(BetStatus.PENDING);
            newBet.setCreatedAt(Instant.now());
            newBet.setAmount(bet.getAmount());
            if (ODDS.equals(predictionRequest.getTicketType())) {
                newBet.setPotentialWinnings(calculatePotentialWinnings(bet));
                newBet.setTaxAmount(calculateTax(newBet.getPotentialWinnings()));
                newBet.setFinalWinnings(newBet.getPotentialWinnings().subtract(newBet.getTaxAmount()));
            } else {
                newBet.setPotentialWinnings(BigDecimal.ZERO);
                newBet.setTaxAmount(BigDecimal.ZERO);
                newBet.setFinalWinnings(BigDecimal.ZERO);
            }
            newBet = betRepository.save(newBet);

            for (PredictionRequest.Bet.Event event : bet.getEvents()) {
                BetItem betItem = new BetItem();
                betItem.setBet(newBet);
                betItem.setEvent(event.getEventId());
                betItem.setOutcomeId(event.getPrediction());
                betItem.setOdds(event.getOdds());
                betItem.setStatus(BetStatus.PENDING);
                betItem.setCreatedAt(Instant.now());
                betItemRepository.save(betItem);
            }
        }

        //debit user account
        DebitRequest debitRequest = new DebitRequest();
        debitRequest.setUserId(predictionRequest.getUserId());
        debitRequest.setAmount(ticket.getTotalAmount().doubleValue());
        debitRequest.setTransactionType(Enumerations.TRANSACTION_TYPE.BB_BET);
        debitRequest.setReference(ticket.getId().toString().concat(" - ").concat(ticket.getType().name()));

        ApiResponse<String> response = userFeignClient.play(debitRequest);

        if (!response.getSuccess()) {
            throw new AppException(response.getMessage());
        }
    }

    @Override
    public List<BetDto> getUserBets(BetFilterCriteria betFilterCriteria, int pageNum, int size) {
        PageRequest pageRequest = PageRequest.of(pageNum, size);
        Specification<Bet> specification = BetSearchSpecification.getBetCriteria(betFilterCriteria);
        Page<Bet> page = betRepository.findAll(specification, pageRequest);
        return page.getContent().stream()
                .filter(Objects::nonNull)
                .map(bet -> BetDto.builder()
                        .id(bet.getId())
                        .betType(bet.getBetType())
                        .status(bet.getStatus())
                        .createdAt(bet.getCreatedAt())
                        .updatedAt(bet.getUpdatedAt())
                        .potentialWinnings(bet.getPotentialWinnings())
                        .taxAmount(bet.getTaxAmount())
                        .finalWinnings(bet.getFinalWinnings())
                        .amount(bet.getAmount())
                        .betItems(getBetItems(bet))
                        .build())
                .toList();
    }

    private static List<BetItemDto> getBetItems(Bet bet) {
        return bet.getBetItems().stream()
                .map(betItem -> BetItemDto.builder()
                        .id(betItem.getId())
                        .event(betItem.getEvent())
                        .outcomeId(betItem.getOutcomeId())
                        .odds(betItem.getOdds())
                        .status(betItem.getStatus())
                        .build())
                .toList();
    }

    private BigDecimal calculateTotalAmount(List<PredictionRequest.Bet> bets) {
        return bets.stream()
                .map(PredictionRequest.Bet::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateTotalOdds(List<PredictionRequest.Bet> bets) {
        return bets.stream()
                .map(this::calculateBetOdds)
                .reduce(BigDecimal.ONE, BigDecimal::multiply);
    }

    private BigDecimal calculateBetOdds(PredictionRequest.Bet bet) {
        return bet.getEvents().stream()
                .map(PredictionRequest.Bet.Event::getOdds)
                .reduce(BigDecimal.ONE, BigDecimal::multiply);
    }

    public BigDecimal calculatePotentialWinnings(PredictionRequest.Bet bet) {
        BigDecimal betOdds = calculateBetOdds(bet);
        return bet.getAmount().multiply(betOdds);
    }

    private BigDecimal calculateTax(BigDecimal amount) {
        return amount.multiply(TAX_RATE);
    }
}
