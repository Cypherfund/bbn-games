package com.cypherfund.bbn.dao.entity;

import com.cypherfund.bbn.utils.Enumerations.TicketType;
import com.cypherfund.bbn.utils.Enumerations.TicketStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TicketType type;

    @Column(name = "total_amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "total_odds", nullable = false, precision = 19, scale = 2)
    private BigDecimal totalOdds;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TicketStatus status;

    @Column(name = "correct_predictions", nullable = false)
    private Integer correctPredictions;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;


}