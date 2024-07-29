package com.cypherfund.bbn.dao.repository;

import com.cypherfund.bbn.dao.entity.Bet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BetRepository extends JpaRepository<Bet, Long>, JpaSpecificationExecutor<Bet> {
    List<Bet> findByJackpotId(Integer jackpotId);

    List<Bet> findByTicketId(long ticket);

    @Query(value = "SELECT * FROM bet b INNER JOIN ticket t ON b.ticket_id = t.id WHERE t.user_id = :userId", nativeQuery = true)
    List<Bet> getUserBets(String userId);
}
