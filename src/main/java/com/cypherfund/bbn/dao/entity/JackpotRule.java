package com.cypherfund.bbn.dao.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "jackpot_rules")
public class JackpotRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "jackpot_type", length = 50)
    private String jackpotType;

    @Column(name = "correct_predictions")
    private Integer correctPredictions;

    @Column(name = "prize_percentage", precision = 5, scale = 2)
    private BigDecimal prizePercentage;

    @Column(name = "voided_events_allowed")
    private Integer voidedEventsAllowed;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

}