package com.cypherfund.bbn.dao.repository;

import com.cypherfund.bbn.dao.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.Instant;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer>, JpaSpecificationExecutor<Event> {
    List<Event> findByTournamentIdAndEventDateAfter(Long tournamentId, Instant date);
}