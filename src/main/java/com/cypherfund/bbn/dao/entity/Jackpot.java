package com.cypherfund.bbn.dao.entity;

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
@Table(name = "jackpots")
public class Jackpot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "jackpot_type", length = 50)
    private String jackpotType;

    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "total_stake", precision = 10, scale = 2)
    private BigDecimal totalStake;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @OneToMany(mappedBy = "jackpot")
    private Set<JackpotEvent> jackpotEvents = new LinkedHashSet<>();

}