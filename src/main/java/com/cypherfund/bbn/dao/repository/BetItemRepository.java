package com.cypherfund.bbn.dao.repository;

import com.cypherfund.bbn.dao.entity.BetItem;
import com.cypherfund.bbn.dto.BetItemDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BetItemRepository extends JpaRepository<BetItem, Long> {
    List<BetItem> findByEvent(Integer eventId);

    @Query("SELECT new com.cypherfund.bbn.dto.BetItemDto(b.id, b.event, b.odds, b.status, b.outcomeId, o.description, o.event.name) " +
            "FROM BetItem b JOIN Outcome o ON b.outcomeId = o.id WHERE b.bet.id = ?1")
    List<BetItemDto> findByBetId(Long betId);
}
