package com.cypherfund.bbn.dao.repository;

import com.cypherfund.bbn.dao.entity.Outcome;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutcomeRepository extends JpaRepository<Outcome, Long> {
    List<Outcome> findByEventId(Long eventId);
}