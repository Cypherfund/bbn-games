package com.cypherfund.bbn.dao.repository;

import com.cypherfund.bbn.dao.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}