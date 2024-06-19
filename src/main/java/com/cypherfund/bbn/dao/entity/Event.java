package com.cypherfund.bbn.dao.entity;

import com.cypherfund.bbn.utils.Enumerations;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 50)
    private Enumerations.EVENT_TYPE type;

    @Column(name = "outcome", length = 50)
    private String outcome;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 50)
    private Enumerations.Event_Status status;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "event_date")
    private Instant eventDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    @Override
    public String toString() {
        return this.id + " - " + this.name ;
    }
}