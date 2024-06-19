package com.cypherfund.bbn.services.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminServiceTest {
    @Autowired AdminService adminService;

    @Test
    void getEventsByTournament() {
        System.out.println(adminService.getEventsByTournament(1L));
    }
}