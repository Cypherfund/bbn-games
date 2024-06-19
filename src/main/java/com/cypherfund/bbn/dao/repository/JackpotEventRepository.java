package com.cypherfund.bbn.dao.repository;

import com.cypherfund.bbn.dao.entity.JackpotEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JackpotEventRepository extends JpaRepository<JackpotEvent, Long> {
    List<JackpotEvent> findByEvent_Id(int eventId);
}