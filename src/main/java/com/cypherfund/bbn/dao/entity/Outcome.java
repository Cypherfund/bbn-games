package com.cypherfund.bbn.dao.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "outcome")
public class Outcome {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "odds", precision = 10, scale = 2)
    private BigDecimal odds;

    @Size(max = 255)
    @Column(name = "img_url")
    private String imgUrl;

}