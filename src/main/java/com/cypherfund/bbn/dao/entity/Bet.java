package com.cypherfund.bbn.dao.entity;

import com.cypherfund.bbn.utils.Enumerations.BetStatus;
import com.cypherfund.bbn.utils.Enumerations.BetType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "bet")
public class Bet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    @Enumerated(EnumType.STRING)
    @Column(name = "bet_type", nullable = false)
    private BetType betType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BetStatus status;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "potential_winnings", precision = 10, scale = 2)
    private BigDecimal potentialWinnings;

    @Column(name = "tax_amount", precision = 10, scale = 2)
    private BigDecimal taxAmount;

    @Column(name = "final_winnings", precision = 10, scale = 2)
    private BigDecimal finalWinnings;

    @OneToMany(mappedBy = "bet", fetch = FetchType.LAZY)
    private Set<BetItem> betItems = new LinkedHashSet<>();

    @Column(name = "amount", precision = 10, scale = 2)
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jackpot_id")
    private Jackpot jackpot;

}
