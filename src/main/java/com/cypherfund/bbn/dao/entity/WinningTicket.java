package com.cypherfund.bbn.dao.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "winning_ticket")
public class WinningTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "total_winnings", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalWinnings;

    @Column(name = "tax_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal taxAmount;

    @Column(name = "final_winnings", nullable = false, precision = 10, scale = 2)
    private BigDecimal finalWinnings;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "user_id", length = 50)
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bet_id")
    private Bet bet;

}