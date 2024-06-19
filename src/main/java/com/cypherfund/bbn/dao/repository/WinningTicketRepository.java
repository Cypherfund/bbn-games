package com.cypherfund.bbn.dao.repository;

import com.cypherfund.bbn.dao.entity.WinningTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WinningTicketRepository extends JpaRepository<WinningTicket, Long> {
}