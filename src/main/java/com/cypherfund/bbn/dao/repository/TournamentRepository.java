package com.cypherfund.bbn.dao.repository;

import com.cypherfund.bbn.dao.entity.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public interface TournamentRepository extends JpaRepository<Tournament, Long> {
    List<Tournament> findAllByCategoryIdAndEndDateAfter(long categoryId, LocalDate date);
}