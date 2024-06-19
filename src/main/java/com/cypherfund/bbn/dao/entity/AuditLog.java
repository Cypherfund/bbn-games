package com.cypherfund.bbn.dao.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "audit_log")
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "entity_id", nullable = false, length = 50)
    private String entityId;

    @Column(name = "entity_type", nullable = false)
    private String entityType;

    @Column(name = "message")
    private String message;

    @Column(name = "action_type", nullable = false, length = 50)
    private String actionType;

    @Column(name = "old_value", precision = 10, scale = 2)
    private BigDecimal oldValue;

    @Column(name = "new_value", precision = 10, scale = 2)
    private BigDecimal newValue;

    @Column(name = "created_at")
    private Instant createdAt;

}