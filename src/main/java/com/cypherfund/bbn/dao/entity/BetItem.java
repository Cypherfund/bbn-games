package com.cypherfund.bbn.dao.entity;

import com.cypherfund.bbn.utils.Enumerations.BetStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "bet_item")
public class BetItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bet_id", nullable = false)
    private Bet bet;

    @Column(name = "event_id", nullable = false)
    private Long event;

    @Column(name = "odds", nullable = false, precision = 19, scale = 2)
    private BigDecimal odds;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BetStatus status;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "outcome_id", nullable = false)
    private Long outcomeId;

}
