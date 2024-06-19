package com.cypherfund.bbn.dao.repository;

import com.cypherfund.bbn.dao.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}