package com.cypherfund.bbn.dao.repository;

import com.cypherfund.bbn.dao.entity.Bet;
import com.cypherfund.bbn.dao.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BetRepository extends JpaRepository<Bet, Long> {
    List<Bet> findByJackpotId(Integer jackpotId);

    List<Bet> findByTicketId(long ticket);
}