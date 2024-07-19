package com.cypherfund.bbn.dao.repository;

import com.cypherfund.bbn.dao.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByUserId(String userId);
}