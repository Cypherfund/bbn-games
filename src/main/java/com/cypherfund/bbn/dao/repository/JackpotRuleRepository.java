package com.cypherfund.bbn.dao.repository;

import com.cypherfund.bbn.dao.entity.JackpotRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JackpotRuleRepository extends JpaRepository<JackpotRule, Integer> {
    List<JackpotRule> findAllByJackpotTypeAndVoidedEventsAllowed(String jackpotType, int voidedEventsAllowed);
}