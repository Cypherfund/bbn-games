package com.cypherfund.bbn.dao.repository;

import com.cypherfund.bbn.dao.entity.BetItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BetItemRepository extends JpaRepository<BetItem, Long> {
    List<BetItem> findByEvent(Integer eventId);
}