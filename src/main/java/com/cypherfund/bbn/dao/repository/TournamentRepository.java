package com.cypherfund.bbn.dao.repository;

import com.cypherfund.bbn.dao.entity.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TournamentRepository extends JpaRepository<Tournament, Long> {
}