package com.cypherfund.bbn.services.impl;

import com.cypherfund.bbn.dao.specifications.filters.EventFilterCriteria;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AdminServiceTest {
    @Autowired AdminService adminService;

    @Test
    void getEventsByTournament() {
        EventFilterCriteria eventFilterCriteria = EventFilterCriteria.builder().tournamentId(1L).build();
        System.out.println(adminService.getEventsByTournament(eventFilterCriteria));
    }
}